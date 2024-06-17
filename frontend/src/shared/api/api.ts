import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { type StoreSchema } from 'app/providers/StoreProvider/config/store';

export const api = createApi({
  baseQuery: fetchBaseQuery({
    baseUrl: '/',
    prepareHeaders: (headers, { getState }) => {
      const accessToken = (getState() as StoreSchema).user.authData?.accessToken;

      if (accessToken) {
        headers.set('Authorization', `Bearer ${accessToken}`);
      }
      return headers;
    },
    credentials: 'include',
  }),
  endpoints: () => ({}),
});
