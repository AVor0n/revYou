import { Breadcrumbs, FirstDisplayedItemsCount, LastDisplayedItemsCount } from '@gravity-ui/uikit';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { getReviewInfo, getSelectedHomework, homeworkActions, useAppDispatch } from 'app';
import styles from './Header.module.scss';

export const Header = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { role } = useParams<{ role: 'student' | 'reviewer' | 'teacher' }>();
  const selectedHomework = useSelector(getSelectedHomework);
  const selectedReview = useSelector(getReviewInfo);

  const goToHomeworks = () => {
    navigate('/homeworks');
    dispatch(homeworkActions.setSelectedHomework(null));
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

  return (
    <div className={styles.header}>
      <Breadcrumbs
        items={[
          {
            text: 'Домашки',
            action: goToHomeworks,
          },
          {
            text: selectedHomework?.name ?? 'unknown homework',
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
