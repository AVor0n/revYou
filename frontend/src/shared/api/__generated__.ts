import { api } from './api';
export const addTagTypes = [
  'Auth',
  'Threads',
  'Feedback',
  'Gitlab',
  'Hello',
  'Homeworks',
  'Lecture',
  'Reviews',
  'Solutions',
  'Users',
] as const;
export const injectedRtkApi = api
  .enhanceEndpoints({
    addTagTypes,
  })
  .injectEndpoints({
    endpoints: build => ({
      refreshAccessToken: build.mutation<RefreshAccessTokenApiResponse, RefreshAccessTokenApiArg>({
        query: queryArg => ({
          url: `/api/auth/refresh-access-token`,
          method: 'POST',
          body: queryArg.refreshAccessTokenRequestDto,
        }),
        invalidatesTags: ['Auth'],
      }),
      signIn: build.mutation<SignInApiResponse, SignInApiArg>({
        query: queryArg => ({ url: `/api/auth/sign-in`, method: 'POST', body: queryArg.signInRequest }),
        invalidatesTags: ['Auth'],
      }),
      signUp: build.mutation<SignUpApiResponse, SignUpApiArg>({
        query: queryArg => ({ url: `/api/auth/sign-up`, method: 'POST', body: queryArg.signUpRequest }),
        invalidatesTags: ['Auth'],
      }),
      addComment: build.mutation<AddCommentApiResponse, AddCommentApiArg>({
        query: queryArg => ({
          url: `/api/threads/${queryArg.threadId}/comments`,
          method: 'POST',
          body: queryArg.commentPostDto,
        }),
        invalidatesTags: ['Threads'],
      }),
      deleteComment: build.mutation<DeleteCommentApiResponse, DeleteCommentApiArg>({
        query: queryArg => ({ url: `/api/threads/comments/${queryArg.commentId}`, method: 'DELETE' }),
        invalidatesTags: ['Threads'],
      }),
      updateComment: build.mutation<UpdateCommentApiResponse, UpdateCommentApiArg>({
        query: queryArg => ({
          url: `/api/threads/comments/${queryArg.commentId}`,
          method: 'PATCH',
          body: queryArg.commentPostDto,
        }),
        invalidatesTags: ['Threads'],
      }),
      getAllThreads: build.query<GetAllThreadsApiResponse, GetAllThreadsApiArg>({
        query: queryArg => ({ url: `/api/threads/review/${queryArg.reviewId}` }),
        providesTags: ['Threads'],
      }),
      resolveCommentsThread: build.mutation<ResolveCommentsThreadApiResponse, ResolveCommentsThreadApiArg>({
        query: queryArg => ({
          url: `/api/threads/${queryArg.threadId}/resolve`,
          method: 'PATCH',
          body: queryArg.commentsThreadResolveDto,
        }),
        invalidatesTags: ['Threads'],
      }),
      startThread: build.mutation<StartThreadApiResponse, StartThreadApiArg>({
        query: queryArg => ({ url: `/api/threads`, method: 'POST', body: queryArg.threadPost }),
        invalidatesTags: ['Threads'],
      }),
      getFeedbacks: build.query<GetFeedbacksApiResponse, GetFeedbacksApiArg>({
        query: () => ({ url: `/api/feedback` }),
        providesTags: ['Feedback'],
      }),
      createFeedback: build.mutation<CreateFeedbackApiResponse, CreateFeedbackApiArg>({
        query: queryArg => ({ url: `/api/feedback`, method: 'POST', body: queryArg.feedbackPost }),
        invalidatesTags: ['Feedback'],
      }),
      getFeedback: build.query<GetFeedbackApiResponse, GetFeedbackApiArg>({
        query: queryArg => ({ url: `/api/feedback/${queryArg.feedbackId}` }),
        providesTags: ['Feedback'],
      }),
      getDiffs: build.query<GetDiffsApiResponse, GetDiffsApiArg>({
        query: queryArg => ({
          url: `/api/gitlab/projects/${queryArg.projectId}/repository/compare`,
          params: { from: queryArg['from'], to: queryArg.to },
        }),
        providesTags: ['Gitlab'],
      }),
      getRawFile: build.query<GetRawFileApiResponse, GetRawFileApiArg>({
        query: queryArg => ({
          url: `/api/gitlab/projects/${queryArg.projectId}/repository/files/${queryArg.filePath}/raw`,
          params: { ref: queryArg.ref },
        }),
        providesTags: ['Gitlab'],
      }),
      getHello: build.query<GetHelloApiResponse, GetHelloApiArg>({
        query: () => ({ url: `/api/hello` }),
        providesTags: ['Hello'],
      }),
      getHomeworks: build.query<GetHomeworksApiResponse, GetHomeworksApiArg>({
        query: () => ({ url: `/api/homeworks` }),
        providesTags: ['Homeworks'],
      }),
      createHomework: build.mutation<CreateHomeworkApiResponse, CreateHomeworkApiArg>({
        query: queryArg => ({ url: `/api/homeworks`, method: 'POST', body: queryArg.homeworkPost }),
        invalidatesTags: ['Homeworks'],
      }),
      getHomework: build.query<GetHomeworkApiResponse, GetHomeworkApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}` }),
        providesTags: ['Homeworks'],
      }),
      deleteHomework: build.mutation<DeleteHomeworkApiResponse, DeleteHomeworkApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}`, method: 'DELETE' }),
        invalidatesTags: ['Homeworks'],
      }),
      updateHomework: build.mutation<UpdateHomeworkApiResponse, UpdateHomeworkApiArg>({
        query: queryArg => ({
          url: `/api/homeworks/${queryArg.homeworkId}`,
          method: 'PATCH',
          body: queryArg.homeworkPatch,
        }),
        invalidatesTags: ['Homeworks'],
      }),
      getAllLectures: build.query<GetAllLecturesApiResponse, GetAllLecturesApiArg>({
        query: () => ({ url: `/api/lectures` }),
        providesTags: ['Lecture'],
      }),
      createLecture: build.mutation<CreateLectureApiResponse, CreateLectureApiArg>({
        query: queryArg => ({ url: `/api/lectures`, method: 'POST', body: queryArg.lecturePost }),
        invalidatesTags: ['Lecture'],
      }),
      getLecture: build.query<GetLectureApiResponse, GetLectureApiArg>({
        query: queryArg => ({ url: `/api/lectures/${queryArg.lectureId}` }),
        providesTags: ['Lecture'],
      }),
      deleteLecture: build.mutation<DeleteLectureApiResponse, DeleteLectureApiArg>({
        query: queryArg => ({ url: `/api/lectures/${queryArg.lectureId}`, method: 'DELETE' }),
        invalidatesTags: ['Lecture'],
      }),
      patchLecture: build.mutation<PatchLectureApiResponse, PatchLectureApiArg>({
        query: queryArg => ({
          url: `/api/lectures/${queryArg.lectureId}`,
          method: 'PATCH',
          body: queryArg.lecturePatch,
        }),
        invalidatesTags: ['Lecture'],
      }),
      addReviewResolution: build.mutation<AddReviewResolutionApiResponse, AddReviewResolutionApiArg>({
        query: queryArg => ({
          url: `/api/homeworks/${queryArg.homeworkId}/reviews/${queryArg.reviewId}/resolution`,
          method: 'PATCH',
          body: queryArg.reviewResolution,
        }),
        invalidatesTags: ['Reviews'],
      }),
      createReview: build.mutation<CreateReviewApiResponse, CreateReviewApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}/reviews/request-review`, method: 'POST' }),
        invalidatesTags: ['Reviews'],
      }),
      getAvailableReviewers: build.query<GetAvailableReviewersApiResponse, GetAvailableReviewersApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}/reviews/available-reviewers` }),
        providesTags: ['Reviews'],
      }),
      getMyReviews: build.query<GetMyReviewsApiResponse, GetMyReviewsApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}/reviews/my-reviews` }),
        providesTags: ['Reviews'],
      }),
      getReviewsInfo: build.query<GetReviewsInfoApiResponse, GetReviewsInfoApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}/reviews/reviews-info` }),
        providesTags: ['Reviews'],
      }),
      getReviewsToDo: build.query<GetReviewsToDoApiResponse, GetReviewsToDoApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}/reviews/reviews-to-do` }),
        providesTags: ['Reviews'],
      }),
      replaceReviewer: build.mutation<ReplaceReviewerApiResponse, ReplaceReviewerApiArg>({
        query: queryArg => ({
          url: `/api/homeworks/${queryArg.homeworkId}/reviews/assign-reviewer`,
          method: 'PATCH',
          body: queryArg.reviewerChange,
        }),
        invalidatesTags: ['Reviews'],
      }),
      startReview: build.mutation<StartReviewApiResponse, StartReviewApiArg>({
        query: queryArg => ({
          url: `/api/homeworks/${queryArg.homeworkId}/reviews/${queryArg.reviewId}/start`,
          method: 'POST',
        }),
        invalidatesTags: ['Reviews'],
      }),
      uploadCorrections: build.mutation<UploadCorrectionsApiResponse, UploadCorrectionsApiArg>({
        query: queryArg => ({
          url: `/api/homeworks/${queryArg.homeworkId}/reviews/${queryArg.reviewId}/upload-corrections`,
          method: 'POST',
        }),
        invalidatesTags: ['Reviews'],
      }),
      approveStudent: build.mutation<ApproveStudentApiResponse, ApproveStudentApiArg>({
        query: queryArg => ({
          url: `/api/homeworks/${queryArg.homeworkId}/solutions/approve-student/${queryArg.studentId}`,
          method: 'PATCH',
        }),
        invalidatesTags: ['Solutions'],
      }),
      readSolutions: build.query<ReadSolutionsApiResponse, ReadSolutionsApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}/solutions` }),
        providesTags: ['Solutions'],
      }),
      createSolution: build.mutation<CreateSolutionApiResponse, CreateSolutionApiArg>({
        query: queryArg => ({
          url: `/api/homeworks/${queryArg.homeworkId}/solutions`,
          method: 'POST',
          body: queryArg.solutionPost,
        }),
        invalidatesTags: ['Solutions'],
      }),
      deleteSolution: build.mutation<DeleteSolutionApiResponse, DeleteSolutionApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}/solutions`, method: 'DELETE' }),
        invalidatesTags: ['Solutions'],
      }),
      updateSolution: build.mutation<UpdateSolutionApiResponse, UpdateSolutionApiArg>({
        query: queryArg => ({
          url: `/api/homeworks/${queryArg.homeworkId}/solutions`,
          method: 'PATCH',
          body: queryArg.solutionPatch,
        }),
        invalidatesTags: ['Solutions'],
      }),
      readSolution: build.query<ReadSolutionApiResponse, ReadSolutionApiArg>({
        query: queryArg => ({ url: `/api/homeworks/${queryArg.homeworkId}/solutions/student-solution` }),
        providesTags: ['Solutions'],
      }),
      updatePassword: build.mutation<UpdatePasswordApiResponse, UpdatePasswordApiArg>({
        query: queryArg => ({ url: `/api/users/update-password`, method: 'PATCH', body: queryArg.passwordPatch }),
        invalidatesTags: ['Users'],
      }),
      updateProfile: build.mutation<UpdateProfileApiResponse, UpdateProfileApiArg>({
        query: queryArg => ({ url: `/api/users/update-profile`, method: 'PATCH', body: queryArg.userPatch }),
        invalidatesTags: ['Users'],
      }),
    }),
    overrideExisting: false,
  });
export { injectedRtkApi as enhancedApi };
export type RefreshAccessTokenApiResponse = /** status 200 Successful operation */ SignInResponse;
export type RefreshAccessTokenApiArg = {
  refreshAccessTokenRequestDto: RefreshAccessTokenRequestDto;
};
export type SignInApiResponse = /** status 200 Successful operation */ SignInResponse;
export type SignInApiArg = {
  signInRequest: SignInRequest;
};
export type SignUpApiResponse = /** status 200 Successful operation */ User;
export type SignUpApiArg = {
  signUpRequest: SignUpRequest;
};
export type AddCommentApiResponse = /** status 201 Created */ Comment;
export type AddCommentApiArg = {
  threadId: number;
  commentPostDto: CommentPostDto;
};
export type DeleteCommentApiResponse = /** status 204 No content */ void;
export type DeleteCommentApiArg = {
  commentId: number;
};
export type UpdateCommentApiResponse = /** status 200 Successful operation */ CommentPostDto;
export type UpdateCommentApiArg = {
  commentId: number;
  commentPostDto: CommentPostDto;
};
export type GetAllThreadsApiResponse = /** status 200 Successful operation */ ThreadWrapper;
export type GetAllThreadsApiArg = {
  reviewId: number;
};
export type ResolveCommentsThreadApiResponse = /** status 200 Successful operation */ CommentsThread;
export type ResolveCommentsThreadApiArg = {
  threadId: number;
  commentsThreadResolveDto: CommentsThreadResolveDto;
};
export type StartThreadApiResponse = /** status 201 Created */ CommentsThread;
export type StartThreadApiArg = {
  threadPost: ThreadPost;
};
export type GetFeedbacksApiResponse = /** status 200 Successful operation */ FeedbackWrapper;
export type GetFeedbacksApiArg = void;
export type CreateFeedbackApiResponse = /** status 201 Created */ FeedbackPost;
export type CreateFeedbackApiArg = {
  feedbackPost: FeedbackPost;
};
export type GetFeedbackApiResponse = /** status 200 Successful operation */ Feedback;
export type GetFeedbackApiArg = {
  feedbackId: number;
};
export type GetDiffsApiResponse = /** status 200 Successful operation */ DiffsWrapper;
export type GetDiffsApiArg = {
  projectId: number;
  /** Commit SHA */
  from: string;
  /** Commit SHA */
  to: string;
};
export type GetRawFileApiResponse = /** status 200 Successful operation */ string;
export type GetRawFileApiArg = {
  projectId: number;
  /** File path (not URL-encoded) */
  filePath: string;
  /** Commit SHA */
  ref: string;
};
export type GetHelloApiResponse = unknown;
export type GetHelloApiArg = void;
export type GetHomeworksApiResponse = /** status 200 Successful operation */ HomeworksWrapper;
export type GetHomeworksApiArg = void;
export type CreateHomeworkApiResponse = /** status 201 Created */ HomeworkPostResponse;
export type CreateHomeworkApiArg = {
  homeworkPost: HomeworkPost;
};
export type GetHomeworkApiResponse = /** status 200 Successful operation */ Homework;
export type GetHomeworkApiArg = {
  homeworkId: number;
};
export type DeleteHomeworkApiResponse = /** status 204 No content */ void;
export type DeleteHomeworkApiArg = {
  homeworkId: number;
};
export type UpdateHomeworkApiResponse = /** status 200 Successful operation */ void;
export type UpdateHomeworkApiArg = {
  homeworkId: number;
  homeworkPatch: HomeworkPatch;
};
export type GetAllLecturesApiResponse = /** status 200 Successful operation */ LectureWrapper;
export type GetAllLecturesApiArg = void;
export type CreateLectureApiResponse = /** status 201 Created */ LecturePostResponse;
export type CreateLectureApiArg = {
  lecturePost: LecturePost;
};
export type GetLectureApiResponse = /** status 200 Successful operation */ Lecture;
export type GetLectureApiArg = {
  lectureId: number;
};
export type DeleteLectureApiResponse = /** status 200 Successful operation */ Lecture;
export type DeleteLectureApiArg = {
  lectureId: number;
};
export type PatchLectureApiResponse = /** status 200 Successful operation */ Lecture;
export type PatchLectureApiArg = {
  lectureId: number;
  lecturePatch: LecturePatch;
};
export type AddReviewResolutionApiResponse = /** status 201 Created */ void;
export type AddReviewResolutionApiArg = {
  homeworkId: number;
  reviewId: number;
  reviewResolution: ReviewResolution;
};
export type CreateReviewApiResponse = /** status 201 Created */ void;
export type CreateReviewApiArg = {
  homeworkId: number;
};
export type GetAvailableReviewersApiResponse = /** status 200 Successful operation */ UserWrapper;
export type GetAvailableReviewersApiArg = {
  homeworkId: number;
};
export type GetMyReviewsApiResponse = /** status 200 Successful operation */ ReviewWrapper;
export type GetMyReviewsApiArg = {
  homeworkId: number;
};
export type GetReviewsInfoApiResponse = /** status 200 Successful operation */ ReviewInfoWrapper;
export type GetReviewsInfoApiArg = {
  homeworkId: number;
};
export type GetReviewsToDoApiResponse = /** status 200 Successful operation */ ReviewWrapper;
export type GetReviewsToDoApiArg = {
  homeworkId: number;
};
export type ReplaceReviewerApiResponse = /** status 200 Review is assigned */ void;
export type ReplaceReviewerApiArg = {
  homeworkId: number;
  reviewerChange: ReviewerChange;
};
export type StartReviewApiResponse = /** status 201 Created */ void;
export type StartReviewApiArg = {
  homeworkId: number;
  reviewId: number;
};
export type UploadCorrectionsApiResponse = /** status 201 Created */ void;
export type UploadCorrectionsApiArg = {
  homeworkId: number;
  reviewId: number;
};
export type ApproveStudentApiResponse = /** status 200 Successful operation */ void;
export type ApproveStudentApiArg = {
  homeworkId: number;
  studentId: number;
};
export type ReadSolutionsApiResponse = /** status 200 Successful operation */ SolutionWrapper;
export type ReadSolutionsApiArg = {
  homeworkId: number;
};
export type CreateSolutionApiResponse = /** status 201 Created */ Solution;
export type CreateSolutionApiArg = {
  homeworkId: number;
  solutionPost: SolutionPost;
};
export type DeleteSolutionApiResponse = /** status 204 No content */ void;
export type DeleteSolutionApiArg = {
  homeworkId: number;
};
export type UpdateSolutionApiResponse = /** status 200 Successful operation */ Solution;
export type UpdateSolutionApiArg = {
  homeworkId: number;
  solutionPatch: SolutionPatch;
};
export type ReadSolutionApiResponse = /** status 200 Successful operation */ Solution;
export type ReadSolutionApiArg = {
  homeworkId: number;
};
export type UpdatePasswordApiResponse = /** status 200 Successful operation */ void;
export type UpdatePasswordApiArg = {
  passwordPatch: PasswordPatch;
};
export type UpdateProfileApiResponse = /** status 200 Successful operation */ UserInfo;
export type UpdateProfileApiArg = {
  userPatch: UserPatch;
};
export type UserRole = 'STUDENT' | 'TEACHER' | 'ADMIN';
export type SignInResponse = {
  accessToken: string;
  refreshToken: string;
  userId: number;
  cohortId?: number;
  role: UserRole;
  username: string;
  name?: string;
  surname?: string;
  email: string;
  gitlabUsername?: string;
  mmUsername?: string;
};
export type Exception = {
  timestamp: string;
  status: number;
  message: string;
};
export type RefreshAccessTokenRequestDto = {
  refreshToken: string;
};
export type SignInRequest = {
  username: string;
  password: string;
};
export type User = {
  userId?: number;
  username?: string;
  email?: string;
};
export type SignUpRequest = {
  username: string;
  password: string;
  confirmPassword: string;
  email: string;
};
export type Comment = {
  commentId: number;
  authorId: number;
  content: string;
  createdAt: string;
  updatedAt: string;
};
export type ExceptionValidation = {
  timestamp: string;
  status: number;
  message: string;
  validationMessages: string[];
};
export type CommentPostDto = {
  content: string;
};
export type CommentsThreadStatus = 'ACTIVE' | 'RESOLVED';
export type CommentsThread = {
  threadId: number;
  authorId: number;
  status: CommentsThreadStatus;
  commitSha: string;
  filePath: string;
  startLine: number;
  startSymbol: number;
  endLine: number;
  endSymbol: number;
  comments: Comment[];
};
export type ThreadWrapper = {
  data: CommentsThread[];
};
export type CommentsThreadResolveDto = {
  status: 'ACTIVE' | 'RESOLVED';
};
export type ThreadPost = {
  reviewId: number;
  commitSha: string;
  filePath: string;
  startLine: number;
  startSymbol: number;
  endLine: number;
  endSymbol: number;
  content: string;
};
export type Feedback = {
  feedbackId: number;
  reviewId: number;
  student: User;
  description: string;
  rating: number;
  feedbackDate: string;
};
export type FeedbackWrapper = {
  data: Feedback[];
};
export type FeedbackPost = {
  reviewId: number;
  rating: number;
  description: string;
};
export type Diff = {
  new_path: string;
  old_path: string;
  new_file: boolean;
  renamed_file: boolean;
  deleted_file: boolean;
  generated_file: boolean;
};
export type DiffsWrapper = {
  diffs: Diff[];
};
export type HomeworkAuthor = {
  id: number;
  firstName: string;
  lastName: string;
};
export type HomeworkLecture = {
  id: number;
  name: string;
};
export type SolutionStatus = 'IN_PROGRESS' | 'REVIEW_STAGE' | 'REVIEWER_STAGE' | 'COMPLETE';
export type Homework = {
  id: number;
  name: string;
  topic: string;
  description?: string;
  sourceCommitId?: string;
  departments: string[];
  author: HomeworkAuthor;
  lecture: HomeworkLecture;
  repositoryLink: string;
  startDate: string;
  completionDeadline: string;
  status?: SolutionStatus;
  reviewDuration: 24 | 48;
};
export type HomeworksWrapper = {
  data: Homework[];
};
export type HomeworkPostResponse = {
  id: number;
};
export type HomeworkPost = {
  name: string;
  topic: string;
  description?: string;
  lectureId: number;
  repositoryLink: string;
  startDate: string;
  completionDeadline: string;
  reviewDuration: 24 | 48;
};
export type HomeworkPatch = {
  name?: string;
  topic?: string;
  description?: string;
  startDate?: string;
  completionDeadline?: string;
  reviewDuration?: 24 | 48;
};
export type Lecture = {
  lectureId: number;
  name: string;
  lectureDate: string;
  zoomLink: string;
  presentationLink: string;
  lector: User;
};
export type LectureWrapper = {
  data: Lecture[];
};
export type LecturePostResponse = {
  lectureId?: number;
};
export type LecturePost = {
  name: string;
  lectureDate: string;
  zoomLink?: string;
  presentationLink?: string;
  lectorId: number;
  cohortsId: number[];
};
export type LecturePatch = {
  name?: string;
  lectureDate?: string;
  zoomLink?: string;
  presentationLink?: string;
  lectorId?: number;
};
export type ReviewResolution = {
  status: 'CORRECTIONS_REQUIRED' | 'APPROVED';
  resolution?: string;
};
export type UserDetailDto = {
  userId: number;
  username: string;
  name: string;
  surname: string;
  role: UserRole;
};
export type UserWrapper = {
  data: UserDetailDto[];
};
export type ReviewStatus =
  | 'REVIEWER_SEARCH'
  | 'REVIEWER_FOUND'
  | 'REVIEW_STARTED'
  | 'CORRECTIONS_REQUIRED'
  | 'CORRECTIONS_LOADED'
  | 'APPROVED'
  | 'ARCHIVED';
export type ReviewAttempt = {
  reviewAttemptId: number;
  reviewId: number;
  solutionAttemptId: number;
  createdAt: string;
  finishedAt?: string;
  resolution?: string;
};
export type Review = {
  reviewId: number;
  status: ReviewStatus;
  projectId: number;
  sourceCommitId: string;
  commitId: string;
  reviewAttempts?: ReviewAttempt[];
};
export type ReviewWrapper = {
  data: Review[];
};
export type ReviewDuration = {
  hours: number;
  minutes: number;
};
export type Student = {
  userId: number;
  username: string;
};
export type ReviewInfo = {
  reviewId: number;
  projectId: number;
  commitId: string;
  status: ReviewStatus;
  duration: ReviewDuration;
  student: Student;
  reviewer?: Student;
  reviewAttempts: ReviewAttempt[];
};
export type ReviewInfoWrapper = {
  data: ReviewInfo[];
  sourceCommitId: string;
};
export type ReviewerChange = {
  reviewId: number;
  reviewerId: number;
};
export type SolutionAttempt = {
  commitId: string;
  createdAt: string;
};
export type Solution = {
  status: SolutionStatus;
  projectId: number;
  branch: string;
  sourceCommitId: string;
  approveScore: number;
  reviewScore: number;
  studentId: number;
  solutionAttempts: SolutionAttempt[];
};
export type SolutionWrapper = {
  data: Solution[];
};
export type SolutionPost = {
  branchLink: string;
};
export type SolutionPatch = {
  branchLink: string;
};
export type PasswordPatch = {
  password: string;
  confirmationPassword: string;
};
export type UserInfo = {
  userId: number;
  cohortId?: number;
  role: UserRole;
  username: string;
  name?: string;
  surname?: string;
  email: string;
  gitlabUsername?: string;
  mmUsername?: string;
};
export type UserPatch = {
  mmUsername?: string;
  name?: string;
  surname?: string;
};
export const {
  useRefreshAccessTokenMutation,
  useSignInMutation,
  useSignUpMutation,
  useAddCommentMutation,
  useDeleteCommentMutation,
  useUpdateCommentMutation,
  useGetAllThreadsQuery,
  useLazyGetAllThreadsQuery,
  useResolveCommentsThreadMutation,
  useStartThreadMutation,
  useGetFeedbacksQuery,
  useLazyGetFeedbacksQuery,
  useCreateFeedbackMutation,
  useGetFeedbackQuery,
  useLazyGetFeedbackQuery,
  useGetDiffsQuery,
  useLazyGetDiffsQuery,
  useGetRawFileQuery,
  useLazyGetRawFileQuery,
  useGetHelloQuery,
  useLazyGetHelloQuery,
  useGetHomeworksQuery,
  useLazyGetHomeworksQuery,
  useCreateHomeworkMutation,
  useGetHomeworkQuery,
  useLazyGetHomeworkQuery,
  useDeleteHomeworkMutation,
  useUpdateHomeworkMutation,
  useGetAllLecturesQuery,
  useLazyGetAllLecturesQuery,
  useCreateLectureMutation,
  useGetLectureQuery,
  useLazyGetLectureQuery,
  useDeleteLectureMutation,
  usePatchLectureMutation,
  useAddReviewResolutionMutation,
  useCreateReviewMutation,
  useGetAvailableReviewersQuery,
  useLazyGetAvailableReviewersQuery,
  useGetMyReviewsQuery,
  useLazyGetMyReviewsQuery,
  useGetReviewsInfoQuery,
  useLazyGetReviewsInfoQuery,
  useGetReviewsToDoQuery,
  useLazyGetReviewsToDoQuery,
  useReplaceReviewerMutation,
  useStartReviewMutation,
  useUploadCorrectionsMutation,
  useApproveStudentMutation,
  useReadSolutionsQuery,
  useLazyReadSolutionsQuery,
  useCreateSolutionMutation,
  useDeleteSolutionMutation,
  useUpdateSolutionMutation,
  useReadSolutionQuery,
  useLazyReadSolutionQuery,
  useUpdatePasswordMutation,
  useUpdateProfileMutation,
} = injectedRtkApi;
