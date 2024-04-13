import { Link } from '@gravity-ui/uikit';

interface GitLabLinkProps {
  repositoryLink: string;
}

export const GitLabLink = ({ repositoryLink }: GitLabLinkProps) => (
  <Link href={repositoryLink} target="_blank">
    Ссылка на репозиторий в GitLab
  </Link>
);
