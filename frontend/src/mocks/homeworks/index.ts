import { HttpResponse, delay, http } from 'msw';
import { type GetHomework, type PatchHomework, type PostHomework } from '@domains/__generated__';
import { initialAuthors, initialHomeworks, initialLectures } from './initial';
import type { Homework } from '@domains/custom';

const lectures = new Map(initialLectures.map(lecture => [lecture.id, lecture]));

const authors = new Map(initialAuthors.map(author => [author.id, author]));

let homeworksIdCounter = initialHomeworks.length + 1;
const homeworks = new Map(initialHomeworks.map(homework => [homework.id, homework]));

const homeworkConverter = (homework: Homework): GetHomework => ({
  id: homework.id,
  completionDeadline: homework.completionDeadline,
  description: homework.description,
  name: homework.name,
  repositoryLink: homework.repositoryLink,
  reviewDuraion: homework.reviewDuraion,
  startDate: homework.startDate,
  topic: homework.topic,
  departments: homework.departments,
  author: authors.get(homework.authorId),
  lecture: lectures.get(homework.lectureId),
});

export const homeworksHandlers = [
  http.get<Record<string, never>, Record<string, never>, { data: GetHomework[] }>('/api/homeworks', async () => {
    await delay(200);
    return HttpResponse.json({
      data: Array.from(homeworks.values()).map(homeworkConverter),
    });
  }),
  http.post<{ id: string }, PostHomework, number>('/api/homeworks', async ({ request }) => {
    const id = homeworksIdCounter;
    homeworksIdCounter += 1;
    const body = await request.json();

    homeworks.set(id, {
      id,
      ...body,
      departments: ['frontend', 'backend'],
      description: body.description ?? '',
      repositoryLink: body.repositoryLink ?? '',
      reviewDuraion: body.reviewDuraion,
    });

    return HttpResponse.json(id, { status: 201 });
  }),
  http.get<{ id: string }, Record<string, never>, GetHomework>('/api/homeworks/:id', ({ params }) => {
    const { id } = params;
    const homework = homeworks.get(+id);

    if (!homework) {
      return HttpResponse.json(null, { status: 404 });
    }

    return HttpResponse.json(homeworkConverter(homework));
  }),
  http.patch<{ id: string }, PatchHomework>('/api/homeworks/:id', async ({ params, request }) => {
    const id = +params.id;
    const homework = homeworks.get(+id);

    if (!homework) {
      return HttpResponse.json(null, { status: 404 });
    }

    const updatedHomework = await request.json();
    homeworks.set(id, { ...homework, ...updatedHomework });

    return HttpResponse.json(homework);
  }),
  http.delete('/api/homeworks/:id', ({ params }) => {
    const { id } = params;
    const homework = homeworks.get(+id);

    if (!homework) {
      return HttpResponse.json(id, { status: 204 });
    }

    homeworks.delete(+id);

    return HttpResponse.json(id);
  }),
];
