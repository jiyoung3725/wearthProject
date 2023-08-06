package com.example.demo.db;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.example.demo.vo.EducationVO;
import com.example.demo.vo.LectureVO;
import com.example.demo.vo.OpinionVO;
import com.example.demo.vo.TrainingRequestVO;


public class SchoolDBManager extends DBManager{

	// lecture
		
		// 강의 전체목록 반환 (기본권한)
		public static List<LectureVO> findAllLecture () {
			SqlSession session = sqlSessionFactory.openSession();
			List<LectureVO> list = session.selectList("lecture.findAllLecture");
			session.close();
			return list;
		}
		
		// 강의 전체목록 반환 (회원권한)
		public static List<LectureVO> findAllLectureLogin (String id) {
			SqlSession session = sqlSessionFactory.openSession();
			List<LectureVO> list = session.selectList("lecture.findAllLectureLogin", id);
			session.close();
			return list;
		}
		
		public static LectureVO findByNoLecture(int lecNO){
			LectureVO l = null;
			SqlSession session = sqlSessionFactory.openSession();
			l = session.selectOne("lecture.findByNoLecture", lecNO);
			session.close();
			return l;
		}
		
		public static int insertLecture (LectureVO l) {
			int re = -1;
			SqlSession session=sqlSessionFactory.openSession();
			re = session.insert("lecture.insertLecture", l);
			session.commit();
			session.close();
			return re;
		}
		
		/* JPA 사용으로 삭제예정
		public static int updateLecture(LectureVO l) {
			int re = -1;
			SqlSession session = sqlSessionFactory.openSession(true);
			re = session.update("lecture.updateLecture",l);
			session.close();
			return re;
		}
		
		public static int deleteLecture(LectureVO l) {
			int re = -1;
			SqlSession session =sqlSessionFactory.openSession(true);
			re = session.delete("lecture.deleteLecture",l);
			session.close();
			return re;
		}
		*/
	
		
// education
		//기본 레코드수	
		public static int getTotalRecordEducation(HashMap<String, Object> map)	{
			int n = 0;
			SqlSession session = sqlSessionFactory.openSession();
			n = session.selectOne("education.getTotalRecordEducation", map);
			session.close();
			return n;
		}
		//로그인 레코드수	
		public static int getTotalRecordEducationLogin(HashMap<String, Object> map)	{
			int n = 0;
			SqlSession session = sqlSessionFactory.openSession();
			n = session.selectOne("education.getTotalRecordEducationLogin", map);
			session.close();
			return n;
		}
		// 기본 findAll
		public static List<EducationVO> findAllEducation(HashMap<String, Object> map){
			SqlSession session = sqlSessionFactory.openSession();
			List<EducationVO> list = session.selectList("education.findAllEducation",map);
			session.close();
			return list;
		}
		// 로그인 findAll
		public static List<EducationVO> findAllEducationLogin(HashMap<String, Object> map){
			SqlSession session = sqlSessionFactory.openSession();
			List<EducationVO> list = session.selectList("education.findAllEducationLogin",map);
			session.close();
			return list;
		}	
		
		public static EducationVO findByNoEducation (int eduno) {
			EducationVO e = null;
			SqlSession session = sqlSessionFactory.openSession();
			e = session.selectOne("education.findByNoEducation", eduno);
			session.close();
			return e;
		}
		
	
	
	// TrainingRequest
		public static int getTotalRecordTrainingRequest()	{
			int reqNo = 0;
			SqlSession session = sqlSessionFactory.openSession();
			reqNo = session.selectOne("trainingRequest.getTotalRecordTrainingRequest");
			session.close();
			return reqNo;
		}
		public static List<TrainingRequestVO> findAllTrainingRequest(HashMap<String, Object> map){
			SqlSession session = sqlSessionFactory.openSession();
			List<TrainingRequestVO> list = session.selectList("trainingRequest.findAllTrainingRequest", map);
			session.close();
			return list;
		}
		
		public static TrainingRequestVO findByNoTrainingRequest (int reqNo) {
			TrainingRequestVO t = null;
			SqlSession session = sqlSessionFactory.openSession();
			t = session.selectOne("trainingRequest.findByNoTrainingRequest", reqNo);
			session.close();
			return t;
		}
		/* JPA 사용으로 삭제예정
		public static int insertTrainingRequest (TrainingRequestVO t) {
			int re = -1;
			SqlSession session=sqlSessionFactory.openSession();
			re = session.insert("trainingRequest.insertTrainingRequest", t);
			session.commit();
			session.close();
			return re;
		}
		
		public static int updateTrainingRequest(TrainingRequestVO t) {
			int re = -1;
			SqlSession session = sqlSessionFactory.openSession(true);
			re = session.update("trainingRequest.updateTrainingRequest",t);
			session.close();
			return re;
		}
		
		public static int deleteTrainingRequest(TrainingRequestVO t) {
			int re = -1;
			SqlSession session =sqlSessionFactory.openSession(true);
			re = session.delete("trainingRequest.deleteTrainingRequest",t);
			session.close();
			return re;
		}
		*/
	// opinion
		public static int getTotalRecordOpinion()	{
			int opinionNo = 0;
			SqlSession session = sqlSessionFactory.openSession();
			opinionNo = session.selectOne("opinion.getTotalRecordOpinion");
			session.close();
			return opinionNo;
		}
		public static List<OpinionVO> findAllOpinion(HashMap<String, Object> map){
			SqlSession session = sqlSessionFactory.openSession();
			List<OpinionVO> list = session.selectList("opinion.findAllOpinion", map);
			session.close();
			return list;
		}
		
		public static OpinionVO findByNoOpinion (int opinionNO) {
			OpinionVO o = null;
			SqlSession session = sqlSessionFactory.openSession();
			o = session.selectOne("opinion.findByNoOpinion", opinionNO);
			session.close();
			return o;
		}
		
		public static int insertOpinion (OpinionVO o) {
			int re = -1;
			SqlSession session=sqlSessionFactory.openSession();
			re = session.insert("opinion.insertOpinion", o);
			session.commit();
			session.close();
			return re;
		}
	
		
		public static int updateOpinion(OpinionVO o) {
			int re = -1;
			SqlSession session = sqlSessionFactory.openSession(true);
			re = session.update("opinion.updateOpinion",o);
			session.close();
			return re;
		}
		
		public static int deleteOpinion(OpinionVO o) {
			int re = -1;
			SqlSession session =sqlSessionFactory.openSession(true);
			re = session.delete("opinion.deleteOpinion",o);
			session.close();
			return re;
		}
}