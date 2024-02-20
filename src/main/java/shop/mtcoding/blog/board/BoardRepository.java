package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    @Transactional
    public void delete(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Transactional
    public void update(BoardRequest.UpdateDTO requestDTO, int id) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ?, author = ? where id = ?");
        // 쿼리 완성하기
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, requestDTO.getContent());
        query.setParameter(3, requestDTO.getAuthor());
        query.setParameter(4, id);
        // 쿼리 전송하기
        query.executeUpdate();
        System.out.println("query: " + query);
    }

    public List<Board> selectAll() {
        Query query = em.createNativeQuery("select * from board_tb", Board.class);
        List<Board> boardList = query.getResultList(); // 못 찾으면 빈 컬렉션을 줌 , 크기 0
        return boardList;
    }

    public Board selectOne(int id) {
        Query query = em.createNativeQuery("select * from board_tb where id=?", Board.class);
        query.setParameter(1, id);

        Board board = null;

        try {
            board = (Board) query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return board;
    }

    @Transactional
    public void insert(String title, String content, String author){
        Query query = em.createNativeQuery("insert into board_tb(title, content, author) values(?, ?, ?)");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, author);

        query.executeUpdate();
    }
}
