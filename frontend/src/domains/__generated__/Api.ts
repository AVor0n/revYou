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
  Exception,
  ExceptionValidation,
  Homework,
  HomeworkPatch,
  HomeworkPost,
  HomeworkPostResponse,
  HomeworksWrapper,
  SignInRequest,
  SignInResponse,
  SignUpRequest,
  User,
} from './data-contracts';
import { ContentType, HttpClient, RequestParams } from './http-client';

export class Api<SecurityDataType = unknown> extends HttpClient<SecurityDataType> {
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
   * @response `200` `void` Successful operation
   * @response `403` `Exception` Forbidden request
   */
  deleteHomework = (homeworkId: number, params: RequestParams = {}) =>
    this.request<void, Exception>({
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
   * @response `200` `void` Successful operation
   * @response `400` `ExceptionValidation` Bad request
   * @response `403` `Exception` Forbidden request
   * @response `404` `Exception` Not found
   */
  updateHomework = (homeworkId: number, data: HomeworkPatch, params: RequestParams = {}) =>
    this.request<void, ExceptionValidation | Exception>({
      path: `/api/homeworks/${homeworkId}`,
      method: 'PATCH',
      body: data,
      secure: true,
      type: ContentType.Json,
      ...params,
    });
}
