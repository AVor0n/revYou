import { type ToastProps } from '@gravity-ui/uikit';
import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { useEffect } from 'react';
import { type InferType, number, object, string } from 'yup';

export const apiErrorSchema = object({
  data: object({
    message: string().required(),
    status: number().required(),
    timestamp: string().required(),
  }).required(),
});

export type ApiError = InferType<typeof apiErrorSchema>;

const displayApiError = (error: ApiError, options: ToastProps) => {
  toaster.add({
    ...options,
    theme: 'danger',
    content: error.data.message,
  });
};

const displayUnknownApiError = () => {
  toaster.add({
    name: 'unknown',
    theme: 'danger',
    content: 'Ошибка связи с сервером',
  });
};

export const useApiError = (error: unknown, options: ToastProps) => {
  useEffect(() => {
    if (!error) return;

    if (apiErrorSchema.isValidSync(error)) {
      displayApiError(error, options);
    } else {
      displayUnknownApiError();
    }
  }, [error]);
};
