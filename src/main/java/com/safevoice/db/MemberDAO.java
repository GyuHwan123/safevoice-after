package com.safevoice.db;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.safevoice.model.MemberVO;

public class MemberDAO {

	private SqlSessionFactory factory = MySqlSessionManager.getFactory();

	// 회원 가입
	public int signIn(MemberVO member) {

		SqlSession sqlsession = factory.openSession(true);
		int row = sqlsession.insert("signIn", member);
		sqlsession.close();
		return row;
	}

	// 로그인에서는 세션에 저장할 정보를 받아야 하기 때문에
	// resultVO 객체 형식으로 데이터를 받아 리턴한다.
	public MemberVO login(MemberVO mvo) {

		SqlSession sqlsession = factory.openSession(true);
		MemberVO resultVO = sqlsession.selectOne("login", mvo);
		sqlsession.close();
		return resultVO;
	}

	// 아이디 중복 확인
	public Integer idDuplicateCheck(String id) {
		SqlSession sqlsession = factory.openSession(true);
		Integer count = sqlsession.selectOne("idDuplicateCheck", id);
		sqlsession.close();
		return (count != null) ? count : 0;
	}

	// 회원정보 수정
	public int updateMember(MemberVO paravo) {
		System.out.println("dao : " + paravo.getPw());
		System.out.println("dao : " + paravo.getPostcode());
		SqlSession sqlsession = factory.openSession(true);
		int row = sqlsession.update("updateMember", paravo);
		sqlsession.close();
		return row;

	}

	// 자녀 연결 인증번호 전송

	public int sendCode(MemberVO member) {
		SqlSession sqlsession = factory.openSession(true);
		Integer result = sqlsession.selectOne("checkMemberByIdAndEmail", member);
		sqlsession.close();
		return (result != null) ? result : 0;
	}

	// 패밀리 코드 갱신
	public int updateFamilyCd(MemberVO member) {
		SqlSession sqlsession = factory.openSession(true);
		int row1 = sqlsession.update("updateFamilyCdForParent", member);
		int row2 = sqlsession.update("updateFamilyCdForChild", member);
		sqlsession.close();
		return row1 + row2;
	}

	// 회원 탈퇴
	public int signOut(MemberVO member) {

		SqlSession sqlsession = factory.openSession(true);
		int row = sqlsession.delete("signOut", member);
		sqlsession.close();
		return row;
	}

	// 비밀번호 수정 - 아이디 확인하기
	// Integer 변수로 변환하지 않아도 괜찮은지 연결해서 확인해야 함
	public int findMember(MemberVO member) {

		SqlSession sqlsession = factory.openSession(true);
		int row = sqlsession.selectOne("findMember", member);
		sqlsession.close();
		return row;

	}

	// 비밀번호 수정 - 비밀번호 갱신
	public int updatePw(MemberVO member) {
		// auto-commit true 로 세션 열기
		SqlSession sqlsession = factory.openSession(true);
		try {
			// 여기에 SQL 매퍼의 id(updatePw)와 파라미터(member)를 넘겨줍니다
			int row = sqlsession.update("updatePw", member);
			return row;
		} finally {
			sqlsession.close();
		}
	}

	// 자녀 관리 - 자녀 삭제 (연결 끊기)
	public int disconnectChild(String childId) {
		SqlSession sqlsession = factory.openSession(true);
		int row = sqlsession.update("disconnectChild", childId);
		sqlsession.close();
		return row;
	}

	// 자녀 관리 (리스트)
	public List<MemberVO> selectMyChildren(String id) {
		SqlSession sqlsession = factory.openSession(true);
		List<MemberVO> children = sqlsession.selectList("selectMyChildren", id);
		sqlsession.close();
		return children != null ? children : Collections.emptyList();
	}
	
	public String getChildNameById(String id) {
		SqlSession sqlsession = factory.openSession(true);
		String name = sqlsession.selectOne("getChildNameById", id);
		sqlsession.close();
		return name;
	}
	// 회원 구독 정보 저장 (SubscriptionJson)
	public int saveSubscription(MemberVO member) {
		SqlSession sqlsession = factory.openSession(true);
		int row = sqlsession.update("saveSubscription", member); // UPDATE 쿼리 사용!
		sqlsession.close();
		return row;
	}
	// 소셜 회원 찾기
	public MemberVO findBySocial(String email) {
		SqlSession sqlsession = factory.openSession(true);
	    MemberVO vo = sqlsession.selectOne("findBySocial", email);
	    sqlsession.close();
	    return vo;
	}
	public int insertGoogleMember(MemberVO vo) {
	    SqlSession session = factory.openSession(true);
	    int cnt = session.insert("insertGoogleMember", vo);
	    session.commit();
	    session.close();
	    return cnt;
	}
		

}
