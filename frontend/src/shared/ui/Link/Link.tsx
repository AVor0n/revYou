import { Link as UIKitLink, type LinkProps as UIKitLinkProps } from '@gravity-ui/uikit';
import { type MouseEvent } from 'react';
import { useNavigate } from 'react-router-dom';

export const Link = ({ href, ...props }: UIKitLinkProps) => {
  const navigate = useNavigate();

  const onClick = (event: MouseEvent<HTMLAnchorElement>) => {
    props.onClick?.(event);
    event.preventDefault();
    navigate(href);
  };

  return <UIKitLink href={href} {...props} onClick={onClick} />;
};
