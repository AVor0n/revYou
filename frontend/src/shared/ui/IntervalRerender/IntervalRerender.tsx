import { useEffect, useState, type ReactNode } from 'react';

interface IntervalRerenderProps {
  intervalInMs: number;
  getContent: () => ReactNode;
}

export const IntervalRerender = ({ intervalInMs, getContent }: IntervalRerenderProps) => {
  const [content, setContent] = useState(getContent());

  useEffect(() => {
    const intervalId = setInterval(() => setContent(getContent()), intervalInMs);

    return () => clearInterval(intervalId);
  }, [getContent, intervalInMs]);

  return content;
};
