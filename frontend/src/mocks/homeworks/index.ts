import { HttpResponse, delay, http } from 'msw';
import { type HomeworkPatch, type Homework, type HomeworkPost } from '@domains';
import { type MockHomework, initialAuthors, initialHomeworks, initialLectures } from './initial';

const lectures = new Map(initialLectures.map(lecture => [lecture.id, lecture]));

const authors = new Map(initialAuthors.map(author => [author.id, author]));

let homeworksIdCounter = initialHomeworks.length + 1;
const homeworks = new Map(initialHomeworks.map(homework => [homework.id, homework]));

const homeworkConverter = (homework: MockHomework): Homework => ({
  id: homework.id,
  completionDeadline: homework.completionDeadline,
  description: homework.description,
  name: homework.name,
  repositoryLink: homework.repositoryLink,
  reviewDuration: homework.reviewDuration,
  startDate: homework.startDate,
  topic: homework.topic,
  departments: homework.departments,
  // @ts-expect-error все норм
  author: authors.get(homework.authorId),
  // @ts-expect-error все норм
  lecture: lectures.get(homework.lectureId),
});

export const homeworksHandlers = [
  http.get<Record<string, never>, Record<string, never>, { data: Homework[] }>('/api/homeworks', async () => {
    await delay(300);
    return HttpResponse.json({
      data: Array.from(homeworks.values()).map(homeworkConverter),
    });
  }),
  http.post<{ id: string }, HomeworkPost, number>('/api/homeworks', async ({ request }) => {
    const id = homeworksIdCounter;
    homeworksIdCounter += 1;
    const body = await request.json();

    homeworks.set(id, {
      id,
      ...body,
      authorId: 1,
      departments: ['frontend', 'backend'],
      description: body.description ?? '',
      repositoryLink: body.repositoryLink,
      reviewDuration: body.reviewDuration,
    });

    return HttpResponse.json(id, { status: 201 });
  }),
  http.get<{ id: string }, Record<string, never>, Homework>('/api/homeworks/:id', async ({ params }) => {
    const { id } = params;
    const homework = homeworks.get(+id);
    await delay(300);

    if (!homework) {
      return HttpResponse.json(null, { status: 404 });
    }

    return HttpResponse.json(homeworkConverter(homework));
  }),
  http.patch<{ id: string }, HomeworkPatch>('/api/homeworks/:id', async ({ params, request }) => {
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
