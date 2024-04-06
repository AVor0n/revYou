import { type PostHomework } from './__generated__';

export interface Homework extends Partial<PostHomework> {
  id?: number;
  departments: string[];
}
