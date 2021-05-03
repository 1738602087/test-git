package cn.repeatlink.module.law.scheduler.updatepostcode.dto;
public class FilePostCode implements Cloneable {

	private String postCode;

	private String addr1;

	private String addr2;

	private String addr3;

	private String addr1Hiragana;

	private String addr2Hiragana;

	private String addr3Hiragana;

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getAddr3() {
		return addr3;
	}

	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}

	
	public String getAddr1Hiragana() {
    return addr1Hiragana;
  }

  public String getAddr2Hiragana() {
    return addr2Hiragana;
  }

  public String getAddr3Hiragana() {
    return addr3Hiragana;
  }

  public void setAddr1Hiragana(String addr1Hiragana) {
    this.addr1Hiragana = addr1Hiragana;
  }

  public void setAddr2Hiragana(String addr2Hiragana) {
    this.addr2Hiragana = addr2Hiragana;
  }

  public void setAddr3Hiragana(String addr3Hiragana) {
    this.addr3Hiragana = addr3Hiragana;
  }

  @Override
	public FilePostCode clone() {
		FilePostCode result = null;
		try {
			result = (FilePostCode) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}
}
