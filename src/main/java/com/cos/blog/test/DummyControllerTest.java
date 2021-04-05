package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//data 리턴(파일이 아니라
@RestController
public class DummyControllerTest {

	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch (Exception e) {
			return "삭제 실패 해당아이디는 db에 없음";
		}
		
		
		return "삭제되었습니다. id: "+id;
	}
	
	//save 함수는 id를 전달하지 않으면 인서트해주거
	//아이디 전달하면 해당 아이디에 대한 데이터가 있으면 업테이트, 없으면 인서트 해요
	//email, password
	@Transactional //함수 종료시에 자동 커밋이 됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id: "+id);
		System.out.println("password: "+requestUser.getPassword());
		System.out.println("email: "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
				
		
//		requestUser.setId(id);
//		requestUser.setUsername("ssar");
//		userRepository.save(requestUser);
		
		//userRepository.save(user);
		//더티체킹: 변경 감지해 db 수정
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한 페이지 당 2건의 데이터 리턴받아
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	//{id}주소로 파리미터를 전달 받을 수 있음
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
			}
		});
		return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) { //key=value (약속된 규칙)
		System.out.println("id: "+user.getId());
		System.out.println("username: "+user.getUsername());
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("email: "+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입완료";
	}
}
	

