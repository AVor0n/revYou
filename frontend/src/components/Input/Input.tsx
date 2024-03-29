import { TextInput } from '@gravity-ui/uikit';
import { type ChangeEvent } from 'react';

interface InputProps extends Omit<React.ComponentProps<typeof TextInput>, 'onChange' | 'validationState'> {
  onChange: (value: string) => void;
  hasError: boolean;
}

export const Input = ({ onChange, hasError, ...restProps }: InputProps) => {
  const onChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
    onChange(e.target.value);
  };

  return <TextInput {...restProps} validationState={hasError ? 'invalid' : undefined} onChange={onChangeHandler} />;
};
