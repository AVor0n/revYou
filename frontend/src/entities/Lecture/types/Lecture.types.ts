import { type Lecture } from '@api';

export interface LectureSchema {
  lectures: Lecture[];
  error: string;
}
