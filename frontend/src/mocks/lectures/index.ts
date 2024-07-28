import { HttpResponse, delay, http } from 'msw';
import { type LecturePost, type Lecture } from '@api';
import { initialLectures } from './initial';

let lecturesIdCounter = initialLectures.length + 1;
const lectures = new Map(initialLectures.map(lecture => [lecture.lectureId, lecture]));

export const lecturesHandlers = [
  http.get<Record<string, never>, Record<string, never>, { data: Lecture[] }>('/api/lecture', async () => {
    await delay(300);
    return HttpResponse.json({
      data: Array.from(lectures.values()),
    });
  }),
  http.post<{ id: string }, LecturePost, number>('/api/lecture', async ({ request }) => {
    const lectureId = lecturesIdCounter;
    lecturesIdCounter += 1;
    const body = await request.json();

    lectures.set(lectureId, {
      lectureId,
      lector: {
        email: 'email',
        userId: 1,
        username: 'name',
      },
      zoomLink: '',
      presentationLink: '',
      ...body,
    });

    return HttpResponse.json(lectureId, { status: 201 });
  }),
  http.get<{ id: string }, Record<string, never>, Lecture>('/api/lecture/:id', async ({ params }) => {
    const { id } = params;
    const lecture = lectures.get(+id);
    await delay(300);

    if (!lecture) {
      return HttpResponse.json(null, { status: 404 });
    }

    return HttpResponse.json(lecture);
  }),
];
