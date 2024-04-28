import { HttpResponse, delay, http } from 'msw';
import { type SolutionInfo } from 'app';
import { type MockSolution, initialSolutions } from './initial';

const solutions = new Map(initialSolutions.map(solution => [solution.id, solution]));

export const solutionsHandlers = [
  http.get<{ id: string }, SolutionInfo, MockSolution>('/api/solutions/:id', async ({ params }) => {
    const { id } = params;
    const solution = solutions.get(+id);
    await delay(300);

    if (!solution) {
      return HttpResponse.json(null, { status: 404 });
    }

    return HttpResponse.json(solution);
  }),
];
