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

import {
  Comment,
  CommentPostDto,
  CommentsThread,
  CommentsThreadResolveDto,
  DiffsWrapper,
  Exception,
  ExceptionValidation,
  FeedbackDto,
  FeedbackPost,
  FeedbackWrapper,
  GetDiffsParams,
  GetRawFileParams,
  Homework,
  HomeworkPatch,
  HomeworkPost,
  HomeworkPostResponse,
  HomeworksWrapper,
  Lecture,
  LecturePatch,
  LecturePost,
  LecturePostPesponseDto,
  RefreshAccessTokenRequestDto,
  ReviewInfoWrapper,
  ReviewResolutionDto,
  ReviewWrapper,
  SignInRequest,
  SignInResponse,
  SignUpRequest,
  Solution,
  SolutionPatch,
  SolutionPost,
  SolutionWrapper,
  ThreadPost,
  ThreadWrapper,
  User,
} from './data-contracts';
import { ContentType, HttpClient, RequestParams } from './http-client';

export class Api<SecurityDataType = unknown> extends HttpClient<SecurityDataType> {
  /**
   * No description
   *
   * @tags Auth
   * @name RefreshAccessToken
   * @request POST:/api/auth/refresh-access-token
   * @response `200` `SignInResponse` Successful operation
   * @response `403` `Exception` Bad request
   */
  refreshAccessToken = (data: RefreshAccessTokenRequestDto, params: RequestParams = {}) =>
    this.request<SignInResponse, Exception>({
      path: `/api/auth/refresh-access-token`,
      method: 'POST',
      body: data,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Auth
   * @name SignIn
   * @request POST:/api/auth/sign-in
   * @response `200` `SignInResponse` Successful operation
   * @response `403` `Exception` Bad request
   */
  signIn = (data: SignInRequest, params: RequestParams = {}) =>
    this.request<SignInResponse, Exception>({
      path: `/api/auth/sign-in`,
      method: 'POST',
      body: data,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Auth
   * @name SignUp
   * @request POST:/api/auth/sign-up
   * @response `200` `User` Successful operation
   * @response `403` `Exception` Bad request
   */
  signUp = (data: SignUpRequest, params: RequestParams = {}) =>
    this.request<User, Exception>({
      path: `/api/auth/sign-up`,
      method: 'POST',
      body: data,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Threads
   * @name AddComment
   * @summary Add comment to thread. Available to review participants, admin and teacher
   * @request POST:/api/threads/{threadId}/comments
   * @secure
   * @response `201` `Comment` Created
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  addComment = (threadId: number, data: CommentPostDto, params: RequestParams = {}) =>
    this.request<Comment, ExceptionValidation | Exception>({
      path: `/api/threads/${threadId}/comments`,
      method: 'POST',
      body: data,
      secure: true,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Threads
   * @name DeleteComment
   * @summary Delete comment. Available to author of the comment and admin
   * @request DELETE:/api/threads/comments/{commentId}
   * @secure
   * @response `204` `unknown` No content
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  deleteComment = (commentId: number, params: RequestParams = {}) =>
    this.request<unknown, Exception>({
      path: `/api/threads/comments/${commentId}`,
      method: 'DELETE',
      secure: true,
      ...params,
    });
  /**
   * No description
   *
   * @tags Threads
   * @name UpdateComment
   * @summary Edit comment. Available to author of the comment and admin
   * @request PATCH:/api/threads/comments/{commentId}
   * @secure
   * @response `200` `CommentPostDto` Successful operation
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  updateComment = (commentId: number, data: CommentPostDto, params: RequestParams = {}) =>
    this.request<CommentPostDto, ExceptionValidation | Exception>({
      path: `/api/threads/comments/${commentId}`,
      method: 'PATCH',
      body: data,
      secure: true,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Threads
   * @name GetAllThreads
   * @summary Get all treads and comments by reviewId. Available to review participants, admin and teacher
   * @request GET:/api/threads/review/{reviewId}
   * @secure
   * @response `200` `ThreadWrapper` Successful operation
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  getAllThreads = (reviewId: number, params: RequestParams = {}) =>
    this.request<ThreadWrapper, Exception>({
      path: `/api/threads/review/${reviewId}`,
      method: 'GET',
      secure: true,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Threads
   * @name ResolveCommentsThread
   * @summary Resolve thread. Available to author of the thread and admin
   * @request PATCH:/api/threads/{threadId}/resolve
   * @secure
   * @response `200` `CommentsThread` Successful operation
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  resolveCommentsThread = (threadId: number, data: CommentsThreadResolveDto, params: RequestParams = {}) =>
    this.request<CommentsThread, ExceptionValidation | Exception>({
      path: `/api/threads/${threadId}/resolve`,
      method: 'PATCH',
      body: data,
      secure: true,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Threads
   * @name StartThread
   * @summary Starting thread with first comment. Available to review participants, admin and teacher
   * @request POST:/api/threads
   * @secure
   * @response `201` `CommentsThread` Created
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  startThread = (data: ThreadPost, params: RequestParams = {}) =>
    this.request<CommentsThread, ExceptionValidation | Exception>({
      path: `/api/threads`,
      method: 'POST',
      body: data,
      secure: true,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Feedback
   * @name GetFeedbacks
   * @request GET:/api/feedback
   * @response `200` `FeedbackWrapper` Successful operation
   */
  getFeedbacks = (params: RequestParams = {}) =>
    this.request<FeedbackWrapper, any>({
      path: `/api/feedback`,
      method: 'GET',
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Feedback
   * @name CreateFeedback
   * @request POST:/api/feedback
   * @response `201` `FeedbackPost` Created
   * @response `400` `ExceptionValidation` Bad request
   */
  createFeedback = (data: FeedbackPost, params: RequestParams = {}) =>
    this.request<FeedbackPost, ExceptionValidation>({
      path: `/api/feedback`,
      method: 'POST',
      body: data,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Feedback
   * @name GetFeedback
   * @request GET:/api/feedback/{feedbackId}
   * @response `200` `FeedbackDto` Successful operation
   */
  getFeedback = (feedbackId: number, params: RequestParams = {}) =>
    this.request<FeedbackDto, any>({
      path: `/api/feedback/${feedbackId}`,
      method: 'GET',
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Gitlab
   * @name GetDiffs
   * @request GET:/api/gitlab/projects/{projectId}/repository/compare
   * @secure
   * @response `200` `DiffsWrapper` Successful operation
   * @response `403` `Exception` Forbidden request
   */
  getDiffs = ({ projectId, ...query }: GetDiffsParams, params: RequestParams = {}) =>
    this.request<DiffsWrapper, Exception>({
      path: `/api/gitlab/projects/${projectId}/repository/compare`,
      method: 'GET',
      query: query,
      secure: true,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Gitlab
   * @name GetRawFile
   * @request GET:/api/gitlab/projects/{projectId}/repository/files/{filePath}/raw
   * @secure
   * @response `200` `string` Successful operation
   * @response `403` `Exception` Forbidden request
   */
  getRawFile = ({ projectId, filePath, ...query }: GetRawFileParams, params: RequestParams = {}) =>
    this.request<string, Exception>({
      path: `/api/gitlab/projects/${projectId}/repository/files/${filePath}/raw`,
      method: 'GET',
      query: query,
      secure: true,
      ...params,
    });
  /**
   * No description
   *
   * @tags Hello
   * @name GetHello
   * @request GET:/api/hello
   * @secure
   * @response `default` `string` default response
   */
  getHello = (params: RequestParams = {}) =>
    this.request<any, string>({
      path: `/api/hello`,
      method: 'GET',
      secure: true,
      ...params,
    });
  /**
   * No description
   *
   * @tags Homeworks
   * @name GetHomeworks
   * @request GET:/api/homeworks
   * @secure
   * @response `200` `HomeworksWrapper` Successful operation
   * @response `403` `Exception` Forbidden request
   */
  getHomeworks = (params: RequestParams = {}) =>
    this.request<HomeworksWrapper, Exception>({
      path: `/api/homeworks`,
      method: 'GET',
      secure: true,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Homeworks
   * @name CreateHomework
   * @request POST:/api/homeworks
   * @secure
   * @response `201` `HomeworkPostResponse` Created
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   */
  createHomework = (data: HomeworkPost, params: RequestParams = {}) =>
    this.request<HomeworkPostResponse, ExceptionValidation | Exception>({
      path: `/api/homeworks`,
      method: 'POST',
      body: data,
      secure: true,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Homeworks
   * @name GetHomework
   * @request GET:/api/homeworks/{homeworkId}
   * @secure
   * @response `200` `Homework` Successful operation
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  getHomework = (homeworkId: number, params: RequestParams = {}) =>
    this.request<Homework, Exception>({
      path: `/api/homeworks/${homeworkId}`,
      method: 'GET',
      secure: true,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Homeworks
   * @name DeleteHomework
   * @request DELETE:/api/homeworks/{homeworkId}
   * @secure
   * @response `204` `unknown` No content
   * @response `403` `Exception` Forbidden request
   */
  deleteHomework = (homeworkId: number, params: RequestParams = {}) =>
    this.request<unknown, Exception>({
      path: `/api/homeworks/${homeworkId}`,
      method: 'DELETE',
      secure: true,
      ...params,
    });
  /**
   * No description
   *
   * @tags Homeworks
   * @name UpdateHomework
   * @request PATCH:/api/homeworks/{homeworkId}
   * @secure
   * @response `200` `unknown` Successful operation
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  updateHomework = (homeworkId: number, data: HomeworkPatch, params: RequestParams = {}) =>
    this.request<unknown, ExceptionValidation | Exception>({
      path: `/api/homeworks/${homeworkId}`,
      method: 'PATCH',
      body: data,
      secure: true,
      type: ContentType.Json,
      ...params,
    });
  /**
   * No description
   *
   * @tags Lecture
   * @name GetAllLectures
   * @request GET:/api/lectures
   * @response `200` `Lecture` Successful operation
   */
  getAllLectures = (params: RequestParams = {}) =>
    this.request<Lecture, any>({
      path: `/api/lectures`,
      method: 'GET',
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Lecture
   * @name CreateLecture
   * @request POST:/api/lectures
   * @response `201` `LecturePostPesponseDto` Created
   * @response `400` `ExceptionValidation` Bad request
   */
  createLecture = (data: LecturePost, params: RequestParams = {}) =>
    this.request<LecturePostPesponseDto, ExceptionValidation>({
      path: `/api/lectures`,
      method: 'POST',
      body: data,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Lecture
   * @name GetLecture
   * @request GET:/api/lectures/{lectureId}
   * @response `200` `Lecture` Successful operation
   */
  getLecture = (lectureId: number, params: RequestParams = {}) =>
    this.request<Lecture, any>({
      path: `/api/lectures/${lectureId}`,
      method: 'GET',
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Lecture
   * @name DeleteLecture
   * @request DELETE:/api/lectures/{lectureId}
   * @response `200` `Lecture` Successful operation
   * @response `400` `ExceptionValidation` Bad request
   */
  deleteLecture = (lectureId: number, params: RequestParams = {}) =>
    this.request<Lecture, ExceptionValidation>({
      path: `/api/lectures/${lectureId}`,
      method: 'DELETE',
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Lecture
   * @name PatchLecture
   * @request PATCH:/api/lectures/{lectureId}
   * @response `200` `Lecture` Successful operation
   * @response `400` `ExceptionValidation` Bad request
   */
  patchLecture = (lectureId: number, data: LecturePatch, params: RequestParams = {}) =>
    this.request<Lecture, ExceptionValidation>({
      path: `/api/lectures/${lectureId}`,
      method: 'PATCH',
      body: data,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Reviews
   * @name AddReviewResolution
   * @request PATCH:/api/homeworks/{homeworkId}/reviews/{reviewId}/resolution
   * @secure
   * @response `201` `unknown` Created
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  addReviewResolution = (homeworkId: number, reviewId: number, data: ReviewResolutionDto, params: RequestParams = {}) =>
    this.request<unknown, ExceptionValidation | Exception>({
      path: `/api/homeworks/${homeworkId}/reviews/${reviewId}/resolution`,
      method: 'PATCH',
      body: data,
      secure: true,
      type: ContentType.Json,
      ...params,
    });
  /**
   * No description
   *
   * @tags Reviews
   * @name CreateReview
   * @request POST:/api/homeworks/{homeworkId}/reviews/request-review
   * @secure
   * @response `201` `unknown` Created
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  createReview = (homeworkId: number, params: RequestParams = {}) =>
    this.request<unknown, ExceptionValidation | Exception>({
      path: `/api/homeworks/${homeworkId}/reviews/request-review`,
      method: 'POST',
      secure: true,
      ...params,
    });
  /**
   * No description
   *
   * @tags Reviews
   * @name GetMyReviews
   * @request GET:/api/homeworks/{homeworkId}/reviews/my-reviews
   * @secure
   * @response `200` `ReviewWrapper` Successful operation
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  getMyReviews = (homeworkId: number, params: RequestParams = {}) =>
    this.request<ReviewWrapper, Exception>({
      path: `/api/homeworks/${homeworkId}/reviews/my-reviews`,
      method: 'GET',
      secure: true,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Reviews
   * @name GetReviewsInfo
   * @request GET:/api/homeworks/{homeworkId}/reviews/reviews-info
   * @secure
   * @response `200` `ReviewInfoWrapper` Successful operation
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  getReviewsInfo = (homeworkId: number, params: RequestParams = {}) =>
    this.request<ReviewInfoWrapper, Exception>({
      path: `/api/homeworks/${homeworkId}/reviews/reviews-info`,
      method: 'GET',
      secure: true,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Reviews
   * @name GetReviewsToDo
   * @request GET:/api/homeworks/{homeworkId}/reviews/reviews-to-do
   * @secure
   * @response `200` `ReviewWrapper` Successful operation
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  getReviewsToDo = (homeworkId: number, params: RequestParams = {}) =>
    this.request<ReviewWrapper, Exception>({
      path: `/api/homeworks/${homeworkId}/reviews/reviews-to-do`,
      method: 'GET',
      secure: true,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Reviews
   * @name StartReview
   * @request POST:/api/homeworks/{homeworkId}/reviews/{reviewId}/start
   * @secure
   * @response `201` `unknown` Created
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  startReview = (reviewId: number, homeworkId: string, params: RequestParams = {}) =>
    this.request<unknown, ExceptionValidation | Exception>({
      path: `/api/homeworks/${homeworkId}/reviews/${reviewId}/start`,
      method: 'POST',
      secure: true,
      ...params,
    });
  /**
   * No description
   *
   * @tags Reviews
   * @name UploadCorrections
   * @request POST:/api/homeworks/{homeworkId}/reviews/{reviewId}/upload-corrections
   * @secure
   * @response `201` `unknown` Created
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  uploadCorrections = (homeworkId: number, reviewId: number, params: RequestParams = {}) =>
    this.request<unknown, ExceptionValidation | Exception>({
      path: `/api/homeworks/${homeworkId}/reviews/${reviewId}/upload-corrections`,
      method: 'POST',
      secure: true,
      ...params,
    });
  /**
   * No description
   *
   * @tags Solutions
   * @name ReadSolutions
   * @summary Available only for teachers
   * @request GET:/api/homeworks/{homeworkId}/solutions
   * @secure
   * @response `200` `SolutionWrapper` Successful operation
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  readSolutions = (homeworkId: number, params: RequestParams = {}) =>
    this.request<SolutionWrapper, Exception>({
      path: `/api/homeworks/${homeworkId}/solutions`,
      method: 'GET',
      secure: true,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Solutions
   * @name CreateSolution
   * @summary You can create a solution for a specific homework only once
   * @request POST:/api/homeworks/{homeworkId}/solutions
   * @secure
   * @response `201` `Solution` Created
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  createSolution = (homeworkId: number, data: SolutionPost, params: RequestParams = {}) =>
    this.request<Solution, ExceptionValidation | Exception>({
      path: `/api/homeworks/${homeworkId}/solutions`,
      method: 'POST',
      body: data,
      secure: true,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Solutions
   * @name DeleteSolution
   * @request DELETE:/api/homeworks/{homeworkId}/solutions
   * @secure
   * @response `204` `unknown` No content
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  deleteSolution = (homeworkId: number, params: RequestParams = {}) =>
    this.request<unknown, Exception>({
      path: `/api/homeworks/${homeworkId}/solutions`,
      method: 'DELETE',
      secure: true,
      ...params,
    });
  /**
   * No description
   *
   * @tags Solutions
   * @name UpdateSolution
   * @summary Updating the solution branch is only available if no attempts have been made
   * @request PATCH:/api/homeworks/{homeworkId}/solutions
   * @secure
   * @response `200` `Solution` Successful operation
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  updateSolution = (homeworkId: number, data: SolutionPatch, params: RequestParams = {}) =>
    this.request<Solution, ExceptionValidation | Exception>({
      path: `/api/homeworks/${homeworkId}/solutions`,
      method: 'PATCH',
      body: data,
      secure: true,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags Solutions
   * @name ReadSolution
   * @summary Available only for students
   * @request GET:/api/homeworks/{homeworkId}/solutions/student-solution
   * @secure
   * @response `200` `Solution` Successful operation
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  readSolution = (homeworkId: number, params: RequestParams = {}) =>
    this.request<Solution, Exception>({
      path: `/api/homeworks/${homeworkId}/solutions/student-solution`,
      method: 'GET',
      secure: true,
      format: 'json',
      ...params,
    });
}
