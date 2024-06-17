import { Breadcrumbs, FirstDisplayedItemsCount, LastDisplayedItemsCount, Skeleton } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useLazyGetHomeworkQuery } from '@shared/api';
import { useApiError } from '@shared/utils';
import { useAppSelector } from 'app/hooks';
import styles from './Header.module.scss';

export const Header = () => {
  const { homeworkId } = useParams<{ homeworkId: string }>();
  const navigate = useNavigate();
  const { role } = useParams<{ role: 'student' | 'reviewer' | 'teacher' }>();
  const [loadHomework, { data: selectedHomework, error }] = useLazyGetHomeworkQuery();
  useApiError(error, { name: 'loadHomework', title: 'Ошибка при загрузке домашнего задания' });

  const selectedReview = useAppSelector(state => state.review.reviewInfo);

  useEffect(() => {
    if (homeworkId === undefined) {
      navigate('/homeworks');
    } else {
      loadHomework({ homeworkId: +homeworkId });
    }
  }, [homeworkId, loadHomework, navigate]);

  const goToHomeworks = () => {
    navigate('/homeworks');
  };

  const goToHomework = () => {
    if (selectedHomework?.id) {
      navigate(`/homeworks/${selectedHomework.id}`);
    }
  };

  const goToMySolutions = () => {
    if (selectedHomework?.id) {
      navigate(`/homeworks/${selectedHomework.id}/my-solution`);
    }
  };

  const goToAllSolutions = () => {
    if (selectedHomework?.id) {
      navigate(`/homeworks/${selectedHomework.id}/all-solutions`);
    }
  };

  const goToSolutionsForReview = () => {
    if (selectedHomework?.id) {
      navigate(`/homeworks/${selectedHomework.id}/for-review`);
    }
  };

  if (!selectedHomework) {
    return <Skeleton className={styles.skeleton} />;
  }

  return (
    <div className={styles.header}>
      <Breadcrumbs
        items={[
          {
            text: 'Домашки',
            action: goToHomeworks,
          },
          {
            text: selectedHomework.name,
            action: goToHomework,
          },
          ...(role === 'student'
            ? [
                {
                  text: 'Мои решения',
                  action: goToMySolutions,
                },
              ]
            : []),
          ...(role === 'reviewer'
            ? [
                {
                  text: 'На проверку',
                  action: goToSolutionsForReview,
                },
              ]
            : []),
          ...(role === 'teacher'
            ? [
                {
                  text: 'Все решения',
                  action: goToAllSolutions,
                },
              ]
            : []),
          {
            text: `${selectedReview?.reviewId}`,
            action: () => {},
          },
        ]}
        firstDisplayedItemsCount={FirstDisplayedItemsCount.Zero}
        lastDisplayedItemsCount={LastDisplayedItemsCount.Two}
      />
    </div>
  );
};
