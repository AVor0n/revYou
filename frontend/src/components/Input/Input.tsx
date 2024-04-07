import { PasswordInput } from '@gravity-ui/components';
import { TextInput } from '@gravity-ui/uikit';
import { forwardRef } from 'react';

interface InputProps extends Omit<React.ComponentProps<typeof TextInput>, 'validationState'> {
  hasError?: boolean;
}

export const Input = forwardRef<HTMLInputElement, InputProps>(({ hasError, type, value, ...restProps }, ref) =>
  type !== 'password' ? (
    <TextInput ref={ref} type={type} {...restProps} validationState={hasError ? 'invalid' : undefined} />
  ) : (
    <PasswordInput
      controlRef={ref}
      onUpdate={() => {}}
      value={value ?? ''}
      showRevealButton
      {...restProps}
      validationState={hasError ? 'invalid' : undefined}
    />
  ),
);
