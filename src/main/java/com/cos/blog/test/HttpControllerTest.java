package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청->응답(HTML 파일)
//@Controller

//사용자가 요청->응답(Data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest: ";
	
	@GetMapping("/http/lombok")
	public String lombokTest(){
//		Member m = new Member(1,"ssar","1234","email");
		//넣는 순서 상관 없음
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG+"getter: "+m.getId());
		m.setId(5000);
		System.out.println(TAG+"setter: "+m.getId());
		return "lombok test 완료";
//		Member m2 = new Member();
	}
	
	//인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다
	//@RequestParam int id, @RequestParam String username
	@GetMapping("/http/get")
	public String getTest(Member m) {	//http://localhost:8080/http/get?id=1&username==ssar
	
		return "get요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword();
	}
	
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post요청"+m.getId()+", "+m.getUsername()+", "+m.getPassword();
	}
	
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put요청"+m.getId()+", "+m.getUsername()+", "+m.getPassword();
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete요청";
	}
}
