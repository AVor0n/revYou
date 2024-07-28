import { HttpResponse, bypass, delay, http } from 'msw';
import { initialSolutions } from './initial';
import type { DiffsWrapper, Review, Solution } from '@api';

const solutions = new Map(initialSolutions.map(solution => [solution.studentId, solution]));

export const solutionsHandlers = [
  http.get<{ id: string }, Review, Solution>('/api/solutions/:id', async ({ params }) => {
    const { id } = params;
    const solution = solutions.get(+id);
    await delay(300);

    if (!solution) {
      return HttpResponse.json(null, { status: 404 });
    }

    return HttpResponse.json(solution);
  }),
  http.get<{ homeworkId: string }, Record<string, never>, { data: Solution[] }>(
    '/api/homeworks/:homeworkId/solutions',
    async () => {
      await delay(300);
      return HttpResponse.json({
        data: Array.from(solutions.values()),
      });
    },
  ),
  http.get<{ id: string }, Record<string, never>, Solution>(
    '/api/homeworks/:id/solutions/student-solution',
    async ({ params }) => {
      const { id } = params;
      const solution = solutions.get(+id);
      await delay(300);

      if (!solution) {
        return HttpResponse.json(null, { status: 404 });
      }

      return HttpResponse.json(solution);
    },
  ),
  http.patch<{ id: string }, Solution['status'], Solution>(
    '/api/homeworks/:id/change-status',
    async ({ params, request }) => {
      const id = +params.id;
      const solution = solutions.get(+id);

      if (!solution) {
        return HttpResponse.json(null, { status: 404 });
      }

      const newStatus = await request.json();
      solution.status = newStatus;
      solutions.set(id, solution);

      return HttpResponse.json(solution);
    },
  ),
  http.get<{ projectId: string }, Record<'from' | 'to', string>, DiffsWrapper>(
    '/api/gitlab/projects/:projectId/repository/compare',
    async ({ params, request }) => {
      const { projectId } = params;
      const url = new URL(request.url);
      const from = url.searchParams.get('from');
      const to = url.searchParams.get('to');

      if (!projectId || !from || !to) {
        return HttpResponse.json(null, { status: 404 });
      }

      const response = await fetch(
        bypass(`https://gitlab.com/api/v4/projects/${projectId}/repository/compare?from=${from}&to=${to}`),
      ).then<DiffsWrapper>(res => res.json());

      return HttpResponse.json(response);
    },
  ),
  http.get<{ projectId: string; path: string }, Record<'from' | 'to', string>, string>(
    '/api/gitlab/projects/:projectId/repository/files/*/raw',
    async ({ params, request }) => {
      const { projectId } = params;
      const url = new URL(request.url);
      const path = url.pathname.match(/(?<=\/files\/\/)(.+)(?=\/raw)/)?.[1];
      const ref = url.searchParams.get('ref');

      if (!projectId || !path || !ref) {
        return HttpResponse.json(null, { status: 404 });
      }

      const response = await fetch(
        bypass(
          `https://gitlab.com/api/v4/projects/${projectId}/repository/files/${encodeURIComponent(path)}/raw?ref=${ref}`,
        ),
      ).then<string>(res => res.text());

      if (response === '{"message":"404 File Not Found"}') return HttpResponse.json(null, { status: 404 });
      return HttpResponse.json(response);
    },
  ),
];
