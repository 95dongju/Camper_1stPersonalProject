package com.camper.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.camper.www.dto.GuestDto;

public class GuestDao {
	public static final int EXISTENT = 0;
	public static final int NONEXISTENT = 1;
	public static final int DELETE = 0;
	public static final int ACTIVE = 1;
	public static final int LOGIN_FAIL = 0;
	public static final int LOGIN_SUCCESS = 1;
	public static final int FAIL = 0;
	public static final int SUCCESS = 1;
	private DataSource ds;
	private GuestDao() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
	}
	private static GuestDao instance = new GuestDao();
	public static GuestDao getInstance() {
		return instance;
	}
	// 1. 게스트 로그인
	public int guestLogin(String s_gid, String s_gpw) {
		int result = LOGIN_FAIL;
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
		String sql = "SELECT * FROM MEMBER_GUEST WHERE S_GID = ? AND S_GPW = ? AND G_DEL_YN = 'N'";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s_gid);
			pstmt.setString(2, s_gpw);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = LOGIN_SUCCESS;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs	!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	// 2. 세션에 로그인 정보 저장하기 위해 s_gid로 guestDto 가져오기
	public GuestDto getGuest(String s_gid) {
		GuestDto guest = null;
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
		String sql = "SELECT * FROM MEMBER_GUEST WHERE S_GID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s_gid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				guest = new GuestDto();
				String g_admin_flag = rs.getString("g_admin_flag");
				String g_del_yn = rs.getString("g_del_yn");
				String s_gemail = rs.getString("s_gemail");
				String s_gname = rs.getString("s_gname");
				String s_gnick = rs.getString("s_gnick");
				String s_gphoto = rs.getString("s_gphoto");
				String s_gpw = rs.getString("s_gpw");
				String s_gtel = rs.getString("s_gtel");
				Timestamp g_rdate = rs.getTimestamp("g_rdate");
				guest = new GuestDto(s_gid, s_gpw, s_gemail, s_gname, s_gnick, s_gtel, s_gphoto, g_admin_flag, g_del_yn, g_rdate);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs	!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return guest;
	}
	// 3. 게스트 아이디 중복체크
	public int gidConfirm(String s_gid) {
		int result = EXISTENT;
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
		String sql = "SELECT * FROM MEMBER_GUEST WHERE S_GID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s_gid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = EXISTENT;
			}else {
				result = NONEXISTENT;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs	!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	// 4. 게스트 이메일 중복체크
	public int gemailConfirm(String s_gemail) {
		int result = EXISTENT;
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
		String sql = "SELECT * FROM MEMBER_GUEST WHERE S_GEMAIL = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s_gemail);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = EXISTENT;
			}else {
				result = NONEXISTENT;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs	!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	// 5. 게스트 회원가입
	public int joinGuest(GuestDto guest) {
		int result = FAIL;
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO MEMBER_GUEST " + 
				"    VALUES (?, ?, ?, ?, ?, ?, 'noprofile.jpg', 'G', 'N', SYSDATE)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, guest.getS_gid());
			pstmt.setString(2, guest.getS_gemail());
			pstmt.setString(3, guest.getS_gpw());
			pstmt.setString(4, guest.getS_gname());
			pstmt.setString(5, guest.getS_gnick());
			pstmt.setString(6, guest.getS_gtel());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	// 6. 게스트 정보 수정
	public int modifyGuest(GuestDto guest) {
		System.out.println("수정할 내용 : "+guest);
		int result = FAIL;
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE MEMBER_GUEST SET S_GPW = ?, " + 
				"                        S_GNICK = ?, " + 
				"                        S_GPHOTO = ?" + 
				"                    WHERE S_GID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, guest.getS_gpw());
			pstmt.setString(2, guest.getS_gnick());
			pstmt.setString(3, guest.getS_gphoto());
			pstmt.setString(4, guest.getS_gid());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	// 7. 게스트 탈퇴
	public int withdrawGuest(String s_gid, String s_gpw) {
		int result = FAIL;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE MEMBER_GUEST SET G_DEL_YN = 'Y' WHERE S_GID = ? AND S_GPW = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s_gid);
			pstmt.setString(2, s_gpw);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
}
