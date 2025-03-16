package ru.nextcloudnext.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.nextcloudnext.dto.TaskSearchParameters;
import ru.nextcloudnext.model.Task;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TaskDaoImpl implements TaskDao {
    EntityManager em;

    @Override
    public List<Task> findTasks(Integer skip, Integer take, TaskSearchParameters parameters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> task = cq.from(Task.class);
        List<Predicate> predicates = new ArrayList<>();
        if (parameters.getAuthorId() != null) {
            predicates.add(cb.equal(task.get("author").get("id"), parameters.getAuthorId()));
        }
        if (parameters.getPerformerId() != null) {
            predicates.add(cb.equal(task.get("performer").get("id"), parameters.getPerformerId()));
        }
        String searchValue = parameters.getSearchValue();
        if (searchValue != null && !searchValue.isEmpty()) {
            predicates.add(
                    cb.or(
                            cb.like(cb.upper(task.get("title")), "%" + searchValue.toUpperCase() + "%"),
                            cb.like(cb.upper(task.get("description")), "%" + searchValue.toUpperCase() + "%")
                    ));
        }

        Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[0]));
        if (skip == null || take == null) {
            return em.createQuery(cq.where(finalPredicate)).getResultList();
        } else {
            return em.createQuery(cq.where(finalPredicate))
                    .setFirstResult(skip)
                    .setMaxResults(take)
                    .getResultList();
        }
    }
}
