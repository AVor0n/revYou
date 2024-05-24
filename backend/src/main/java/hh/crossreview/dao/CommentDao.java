package hh.crossreview.dao;

import hh.crossreview.entity.Comment;
import hh.crossreview.entity.CommentsThread;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import java.util.List;

@Named
@Singleton
public class CommentDao extends GenericDao{
  public List<Comment> findByCommentsThread(CommentsThread commentsThread){
    return getEntityManager()
            .createQuery(
        "SELECT c FROM Comment c " +
                "WHERE c.commentsThread = :commentsThread",
                Comment.class
            )
            .setParameter("commentsThread", commentsThread)
            .getResultStream()
            .toList();
  }

  public void deleteComment(Comment comment) {
    getEntityManager().remove(comment);
  }
}
