/* eslint-disable */
/* tslint:disable */
/*
 * ---------------------------------------------------------------
 * ## THIS FILE WAS GENERATED VIA SWAGGER-TYPESCRIPT-API        ##
 * ##                                                           ##
 * ## AUTHOR: acacode                                           ##
 * ## SOURCE: https://github.com/acacode/swagger-typescript-api ##
 * ---------------------------------------------------------------
 */

export interface HomeworkList {
  data?: GetHomework[];
}

export interface GetHomework {
  /**
   * @format int32
   * @example 1
   */
  id?: number;
  /** @example "Основы Git" */
  name?: string;
  /** @example "Git" */
  topic?: string;
  /** @example "Описание домашки" */
  description?: string;
  /** @example ["FRONT","BACK"] */
  departments?: string[];
  author?: Author;
  lecture?: Lecture;
  /** @example "https://gitlab.com/hw_git" */
  repositoryLink?: string;
  /**
   * @format date-time
   * @example "2018-03-20T00:00:00Z"
   */
  startDate?: string;
  /**
   * @format date-time
   * @example "2018-03-25T00:00:00Z"
   */
  completionDeadline?: string;
  /**
   * @format int32
   * @example 48
   */
  reviewDuraion?: number;
}

export interface PostHomework {
  /** @example "Основы Git" */
  name: string;
  /** @example "Git" */
  topic: string;
  /** @example "Описание домашки" */
  description?: string;
  /**
   * @format int32
   * @example 2
   */
  authorId: number;
  /**
   * @format int32
   * @example 3
   */
  lectureId: number;
  /** @example "https://gitlab.com/hw_git" */
  repositoryLink?: string;
  /**
   * @format date-time
   * @example "2018-03-20T00:00:00Z"
   */
  startDate: string;
  /**
   * @format date-time
   * @example "2018-03-25T00:00:00Z"
   */
  completionDeadline: string;
  /**
   * @format int32
   * @example 48
   */
  reviewDuraion: number;
}

export interface PatchHomework {
  /** @example "Основы Git" */
  name?: string;
  /** @example "Git" */
  topic?: string;
  /** @example "Описание домашки" */
  description?: string;
  /** @example "https://gitlab.com/hw_git" */
  repositoryLink?: string;
  /**
   * @format date-time
   * @example "2018-03-20T00:00:00Z"
   */
  startDate?: string;
  /**
   * @format date-time
   * @example "2018-03-25T00:00:00Z"
   */
  completionDeadline?: string;
  /**
   * @format int32
   * @example 48
   */
  reviewDuraion?: number;
}

export interface Author {
  /**
   * @format int32
   * @example 2
   */
  id?: number;
  /** @example "Иван" */
  firstName?: string;
  /** @example "Иванов" */
  lastName?: string;
}

export interface Lecture {
  /**
   * @format int32
   * @example 3
   */
  id?: number;
  /** @example "Лекция по Git" */
  name?: string;
}
