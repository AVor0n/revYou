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

import { GetHomework, HomeworkList, PatchHomework, PostHomework } from './data-contracts';
import { ContentType, HttpClient, RequestParams } from './http-client';

export class Homeworks<SecurityDataType = unknown> extends HttpClient<SecurityDataType> {
  /**
   * @description Get all homeworks
   *
   * @tags homework
   * @name HomeworksList
   * @summary Get all homeworks
   * @request GET:/homeworks
   * @response `200` `HomeworkList` Successful operation
   * @response `403` `void` Forbidden request
   */
  homeworksList = (params: RequestParams = {}) =>
    this.request<HomeworkList, void>({
      path: `/homeworks`,
      method: 'GET',
      ...params,
    });
  /**
 * @description Create homework
 *
 * @tags homework
 * @name HomeworksCreate
 * @summary Create homework
 * @request POST:/homeworks
 * @response `201` `{
  \**
   * Generated homework id
   * @format int32
   * @example 2
   *\
    id?: number,

}` Successful operation
 * @response `400` `void` Bad request
 * @response `403` `void` Forbidden request
 */
  homeworksCreate = (data: PostHomework, params: RequestParams = {}) =>
    this.request<
      {
        /**
         * Generated homework id
         * @format int32
         * @example 2
         */
        id?: number;
      },
      void
    >({
      path: `/homeworks`,
      method: 'POST',
      body: data,
      type: ContentType.Json,
      format: 'json',
      ...params,
    });
  /**
   * No description
   *
   * @tags homework
   * @name HomeworksDetail
   * @request GET:/homeworks/{homeworkId}
   * @response `200` `GetHomework` Successful operation
   * @response `403` `void` Forbidden request
   * @response `404` `void` Homework not found
   */
  homeworksDetail = (homeworkId: number, params: RequestParams = {}) =>
    this.request<GetHomework, void>({
      path: `/homeworks/${homeworkId}`,
      method: 'GET',
      ...params,
    });
  /**
   * No description
   *
   * @tags homework
   * @name HomeworksPartialUpdate
   * @request PATCH:/homeworks/{homeworkId}
   * @response `200` `void` Successful operation
   * @response `400` `void` Bad request
   * @response `403` `void` Forbidden request
   * @response `404` `void` Homework not found
   */
  homeworksPartialUpdate = (homeworkId: number, data: PatchHomework, params: RequestParams = {}) =>
    this.request<void, void>({
      path: `/homeworks/${homeworkId}`,
      method: 'PATCH',
      body: data,
      type: ContentType.Json,
      ...params,
    });
  /**
   * No description
   *
   * @tags homework
   * @name HomeworksDelete
   * @request DELETE:/homeworks/{homeworkId}
   * @response `200` `void` Successful operation
   * @response `403` `void` Forbidden request
   */
  homeworksDelete = (homeworkId: number, params: RequestParams = {}) =>
    this.request<void, void>({
      path: `/homeworks/${homeworkId}`,
      method: 'DELETE',
      ...params,
    });
}
