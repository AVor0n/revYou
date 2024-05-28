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

export interface SignInResponse {
  accessToken?: string;
  refreshToken?: string;
  role?: string;
}

export interface Exception {
  /** @format date-time */
  timestamp: string;
  /** @format int32 */
  status: number;
  message: string;
}

export interface RefreshAccessTokenRequestDto {
  refreshToken: string;
}

export interface SignInRequest {
  username: string;
  password: string;
}

export interface User {
  /** @format int32 */
  userId?: number;
  username?: string;
  email?: string;
}

export interface SignUpRequest {
  username: string;
  password: string;
  confirmPassword: string;
  email: string;
}

export interface Comment {
  /** @format int32 */
  commentId: number;
  /** @format int32 */
  authorId: number;
  content: string;
  /** @format date-time */
  createdAt: string;
  /** @format date-time */
  updatedAt: string;
}

export interface ExceptionValidation {
  /** @format date-time */
  timestamp: string;
  /** @format int32 */
  status: number;
  message: string;
  validationMessages: string[];
}

export interface CommentPostDto {
  content: string;
}

export interface CommentsThread {
  /** @format int32 */
  threadId: number;
  /** @format int32 */
  authorId: number;
  status: CommentsThreadStatusEnum;
  commitSha: string;
  filePath: string;
  /** @format int32 */
  startLine: number;
  /** @format int32 */
  startSymbol: number;
  /** @format int32 */
  endLine: number;
  /** @format int32 */
  endSymbol: number;
  comments: Comment[];
}

export interface ThreadWrapper {
  data: CommentsThread[];
}

export interface ThreadPost {
  /** @format int32 */
  reviewId: number;
  commitSha: string;
  filePath: string;
  /** @format int32 */
  startLine: number;
  /** @format int32 */
  startSymbol: number;
  /** @format int32 */
  endLine: number;
  /** @format int32 */
  endSymbol: number;
  content: string;
}

export interface FeedbackPost {
  /** @format int32 */
  review: number;
  /** @format int32 */
  student: number;
  /** @format int32 */
  rating: number;
  description: string;
}

export interface FeedbackDto {
  /** @format int32 */
  feedbackId?: number;
  /** @format int32 */
  reviewId?: number;
  student?: User;
  description?: string;
  /** @format int32 */
  rating?: number;
  /** @format date-time */
  feedbackDate?: string;
}

export interface FeedbackWrapper {
  data: FeedbackDto[];
}

export interface Diff {
  new_path: string;
  old_path: string;
  new_file: boolean;
  renamed_file: boolean;
  deleted_file: boolean;
  generated_file: boolean;
}

export interface DiffsWrapper {
  diffs: Diff[];
}

export interface HomeworkPostResponse {
  /** @format int32 */
  id: number;
}

export interface HomeworkPost {
  name: string;
  topic: string;
  description?: string;
  /** @format int32 */
  lectureId: number;
  repositoryLink: string;
  /** @format date-time */
  startDate: string;
  /** @format date-time */
  completionDeadline: string;
  /** @format int32 */
  reviewDuration: HomeworkPostReviewDurationEnum;
}

export interface Homework {
  /** @format int32 */
  id: number;
  name: string;
  topic: string;
  description?: string;
  sourceCommitId?: string;
  departments: string[];
  author: HomeworkAuthor;
  lecture: HomeworkLecture;
  repositoryLink: string;
  /** @format date-time */
  startDate: string;
  /** @format date-time */
  completionDeadline: string;
  /** @format int32 */
  reviewDuration: HomeworkReviewDurationEnum;
}

export interface HomeworkAuthor {
  /** @format int32 */
  id: number;
  firstName: string;
  lastName: string;
}

export interface HomeworkLecture {
  /** @format int32 */
  id: number;
  name: string;
}

export interface HomeworksWrapper {
  data: Homework[];
}

export interface HomeworkPatch {
  name?: string;
  topic?: string;
  description?: string;
  /** @format date-time */
  startDate?: string;
  /** @format date-time */
  completionDeadline?: string;
  /** @format int32 */
  reviewDuration?: HomeworkPatchReviewDurationEnum;
}

export interface LecturePostPesponseDto {
  /** @format int32 */
  lectureId?: number;
}

export interface LecturePost {
  name: string;
  /** @format date-time */
  lectureDate: string;
  zoomLink?: string;
  presentationLink?: string;
  /** @format int32 */
  lectorId: number;
  cohortsId: number[];
}

export interface Lecture {
  /** @format int32 */
  lectureId: number;
  name: string;
  /** @format date-time */
  lectureDate: string;
  zoomLink: string;
  presentationLink: string;
  lector: User;
}

export interface LecturePatch {
  name?: string;
  /** @format date-time */
  lectureDate?: string;
  zoomLink?: string;
  presentationLink?: string;
  /** @format int32 */
  lectorId?: number;
}

export interface ReviewResolutionDto {
  status?: string;
  resolution?: string;
}

export interface Review {
  /** @format int32 */
  reviewId?: number;
  status?: string;
  /** @format int32 */
  projectId?: number;
  sourceCommitId?: string;
  commitId?: string;
  reviewAttempts?: ReviewAttempt[];
}

export interface ReviewAttempt {
  /** @format int32 */
  reviewAttemptId?: number;
  /** @format int32 */
  reviewId?: number;
  /** @format int32 */
  solutionAttemptId?: number;
  /** @format date-time */
  createdAt?: string;
  /** @format date-time */
  finishedAt?: string;
  resolution?: string;
}

export interface ReviewWrapper {
  data: Review[];
}

export interface ReviewDuration {
  /** @format int64 */
  hours: number;
  /** @format int64 */
  minutes: number;
}

export interface ReviewInfo {
  /** @format int32 */
  reviewId: number;
  /** @format int32 */
  projectId: number;
  commitId: string;
  status: string;
  duration: ReviewDuration;
  student: Student;
  reviewer: Student;
  reviewAttempts: ReviewAttempt[];
}

export interface ReviewInfoWrapper {
  data: ReviewInfo[];
  sourceCommitId: string;
}

export interface Student {
  /** @format int32 */
  userId: number;
  username: string;
}

export interface Solution {
  status: string;
  /** @format int32 */
  projectId: number;
  branch: string;
  sourceCommitId: string;
  /** @format int32 */
  approveScore: number;
  /** @format int32 */
  reviewScore: number;
  /** @format int32 */
  studentId: number;
  solutionAttempts: SolutionAttempt[];
}

export interface SolutionAttempt {
  commitId: string;
  /** @format date-time */
  createdAt: string;
}

export interface SolutionPost {
  branchLink: string;
}

export interface SolutionWrapper {
  data: Solution[];
}

export interface SolutionPatch {
  branchLink: string;
}

export type CommentsThreadStatusEnum = 'ACTIVE' | 'RESOLVED';

/** @format int32 */
export type HomeworkPostReviewDurationEnum = 24 | 48;

/** @format int32 */
export type HomeworkReviewDurationEnum = 24 | 48;

/** @format int32 */
export type HomeworkPatchReviewDurationEnum = 24 | 48;

export interface GetDiffsParams {
  /** Commit SHA */
  from: string;
  /** Commit SHA */
  to: string;
  /** @format int32 */
  projectId: number;
}

export interface GetRawFileParams {
  /** Commit SHA */
  ref: string;
  /** @format int32 */
  projectId: number;
  /**
   * File path (not URL-encoded)
   * @pattern .+
   */
  filePath: string;
}
