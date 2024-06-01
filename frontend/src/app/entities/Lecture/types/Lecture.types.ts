import { type Lecture } from '@domains';

export interface LectureSchema {
  lectures: Lecture[];
  error: string;
}
