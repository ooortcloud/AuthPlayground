package com.example.demo.domain.google;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleResponse {
	
	private String access_token; // ���ø����̼��� Google API ��û�� �����ϱ� ���� ������ ��ū
    private String expires_in;   // Access Token�� ���� ����
    private String refresh_token;    // �� �׼��� ��ū�� ��� �� ����� �� �ִ� ��ū
    private String scope;
    private String token_type;   // ��ȯ�� ��ū ����(Bearer ����)
    private String id_token;  // �������� ���µ� google server �������� ����
}
