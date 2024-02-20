package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardRepository.class) // 내가 만든 class는 import 해줘야 함 -> 안적으면 null
@DataJpaTest // DB 관련 객체들이 IoC에 뜬다.
public class BoardRepositoryTest {

    @Autowired // test에서 DI하는 코드
    private BoardRepository boardRepository;
    private BoardRequest boardRequest;

    @Test
    public void delete_test(){
        // given
        int id = 1;

        // when
        boardRepository.delete(id);

        // then
        Board deletedBoard = boardRepository.selectOne(id);
        System.out.println(deletedBoard);
    }

    @Test
    public void update_test(){
        // given
        int id = 1;
        BoardRequest.UpdateDTO updateDTO = new BoardRequest.UpdateDTO();
        updateDTO.setTitle("Title");
        updateDTO.setContent("Content");
        updateDTO.setAuthor("Author");

        // when
        boardRepository.update(updateDTO, id);

        // then
        Board updatedBoard = boardRepository.selectOne(id);
        Assertions.assertThat(updatedBoard.getTitle()).isEqualTo("Title");
        Assertions.assertThat(updatedBoard.getContent()).isEqualTo("Content");
        Assertions.assertThat(updatedBoard.getAuthor()).isEqualTo("Author");
    }

    @Test
    public void selectAll_test() {
        // given

        // when
        List<Board> boardList = boardRepository.selectAll();
        System.out.println(boardList.size());

        // then (id=1, title=제목1, content=내용1, author=홍길동)
        // System.out.println(boardList);
        Assertions.assertThat(boardList.get(0).getTitle()).isEqualTo("제목1");
        Assertions.assertThat(boardList.get(0).getContent()).isEqualTo("내용1");
        Assertions.assertThat(boardList.get(0).getAuthor()).isEqualTo("홍길동");
        Assertions.assertThat(boardList.size()).isEqualTo(8);
    }

    @Test
    public void selectOne_test() { // test 메서드는 파라미터가 없음->직접 적어야 함, 리턴이 없음
        // given
        int id = 1; // 자체가 잘못된 것인지 알 수 가 없음
        // when
        Board board = boardRepository.selectOne(id);
        // then -> (상태 검사)
        //System.out.println(board);
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1"); // 실제 값 : 상태 (= 객체의 필드) 검사하기
        Assertions.assertThat(board.getContent()).isEqualTo("내용1");
        Assertions.assertThat(board.getAuthor()).isEqualTo("홍길동");
    } // Rollback (자동)

    @Test
    public void insert_test() { // test 메서드는 파라미터가 없음->직접 적어야 함, 리턴이 없음
        // given
        String title = "제목10";
        String content = "내용10";
        String author = "이순신";

        // when
        boardRepository.insert(title, content, author);

        // then -> 눈으로 확인 (쿼리)
    } // Rollback (자동)
}