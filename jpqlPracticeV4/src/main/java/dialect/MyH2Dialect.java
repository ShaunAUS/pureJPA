package dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;


// JPQL 사용자 정의함수
// 우리가 정의하는게 아니고 DB에 원래 있던 함수들을 JPQL로 추가해주는 것이다.
public class MyH2Dialect extends H2Dialect {

    public MyH2Dialect(){
        registerFunction("group_concat",new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
