import { TextInput } from '@gravity-ui/uikit';
import React from 'react';

interface InputProps extends Omit<React.ComponentProps<typeof TextInput>, 'validationState'> {
  hasError?: boolean;
}

export const Input = React.forwardRef<HTMLInputElement, InputProps>(({ hasError, ...restProps }, ref) => (
  <TextInput ref={ref} {...restProps} validationState={hasError ? 'invalid' : undefined} />
));
