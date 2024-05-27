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
  DiffsWrapper,
  Exception,
  ExceptionValidation,
  FeedbackPost,
  GetDiffsParams,
  GetRawFileParams,
  Homework,
  HomeworkPatch,
  HomeworkPost,
  HomeworkPostResponse,
  HomeworksWrapper,
  RefreshAccessTokenRequestDto,
  ReviewResolutionDto,
  ReviewWrapper,
  SignInRequest,
  SignInResponse,
  SignUpRequest,
  Solution,
  SolutionPatch,
  SolutionPost,
  SolutionWrapper,
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
   * @name GetFeedbacks
   * @request GET:/api/feedbacks
   * @response `default` `unknown` default response
   */
  getFeedbacks = (params: RequestParams = {}) =>
    this.request<any, unknown>({
      path: `/api/feedbacks`,
      method: 'GET',
      ...params,
    });
  /**
   * No description
   *
   * @name CreateFeedback
   * @request POST:/api/feedbacks
   * @response `default` `unknown` default response
   */
  createFeedback = (data: FeedbackPost, params: RequestParams = {}) =>
    this.request<any, unknown>({
      path: `/api/feedbacks`,
      method: 'POST',
      body: data,
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
