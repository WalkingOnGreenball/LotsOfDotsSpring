package dots.of.lots.category.notice.domain;

import java.sql.Timestamp;

public class Notice {

	private int noticeNo;
	private String noticeSubject;
	private String noticeContent;
	private String noticeWriter;
	private String noticeFileName;
	private String noticeFileRename;
	private String noticeFilePath;
	private long noticeFileLength;
	private Timestamp nCreateDate;
	private Timestamp nUpdateDate;
	private char nStatus;
	
	public Notice() {
		super();
	}

	public Notice(String noticeSubject, String noticeContent) {
		super();
		this.noticeSubject = noticeSubject;
		this.noticeContent = noticeContent;
	}

	public Notice(int noticeNo, String noticeSubject, String noticeContent) {
		super();
		this.noticeNo = noticeNo;
		this.noticeSubject = noticeSubject;
		this.noticeContent = noticeContent;
	}

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeSubject() {
		return noticeSubject;
	}

	public void setNoticeSubject(String noticeSubject) {
		this.noticeSubject = noticeSubject;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeWriter() {
		return noticeWriter;
	}

	public void setNoticeWriter(String noticeWriter) {
		this.noticeWriter = noticeWriter;
	}

	public String getNoticeFileName() {
		return noticeFileName;
	}

	public void setNoticeFileName(String noticeFileName) {
		this.noticeFileName = noticeFileName;
	}

	public String getNoticeFileRename() {
		return noticeFileRename;
	}

	public void setNoticeFileRename(String noticeFileRename) {
		this.noticeFileRename = noticeFileRename;
	}

	public String getNoticeFilePath() {
		return noticeFilePath;
	}

	public void setNoticeFilePath(String noticeFilePath) {
		this.noticeFilePath = noticeFilePath;
	}

	public long getNoticeFileLength() {
		return noticeFileLength;
	}

	public void setNoticeFileLength(long noticeFileLength) {
		this.noticeFileLength = noticeFileLength;
	}

	public Timestamp getnCreateDate() {
		return nCreateDate;
	}

	public void setnCreateDate(Timestamp nCreateDate) {
		this.nCreateDate = nCreateDate;
	}

	public Timestamp getnUpdateDate() {
		return nUpdateDate;
	}

	public void setnUpdateDate(Timestamp nUpdateDate) {
		this.nUpdateDate = nUpdateDate;
	}

	public char getnStatus() {
		return nStatus;
	}

	public void setnStatus(char nStatus) {
		this.nStatus = nStatus;
	}

	@Override
	public String toString() {
		return "공지 [번호=" + noticeNo + ", 제목=" + noticeSubject + ", 내용=" + noticeContent
				+ ", 작성자=" + noticeWriter + ", 파일이름=" + noticeFileName + ", 파일리네임="
				+ noticeFileRename + ", 파일패스=" + noticeFilePath + ", 파일길이=" + noticeFileLength
				+ ", 생성일=" + nCreateDate + ", 수정일=" + nUpdateDate + ", 사용여부=" + nStatus + "]";
	}
	
}
