package com.example.demo.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.thymeleaf.dto.SampleDto;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample")
@Log4j2
public class ThymeleafSampleController {

	@GetMapping("/ex01")
	public void ex01() {
		
		log.info("ex01");
	}
	
	@GetMapping({"/ex02", "/exLink", "/exNumber", "/exDate"})
	public void exModel(Model model) {
		
		List<SampleDto> list = IntStream.rangeClosed(1, 20).asLongStream()
				.mapToObj(i -> {
								SampleDto dto = SampleDto.builder()
												.sno(i)
												.first("First"+i)
												.last("Last"+i)
												.regTime(LocalDateTime.now())
												.build();
								return dto;
				}).collect(Collectors.toList());
		
		model.addAttribute("list", list);
	}
	
	@GetMapping({"/exInline"})
	public String exInline(RedirectAttributes redirectAttributes) {
		
		
		SampleDto dto = SampleDto.builder()
				.sno(100L)
				.first("First"+100)
				.last("Last"+100)
				.regTime(LocalDateTime.now())
				.build();
		
		redirectAttributes.addFlashAttribute("result", "success");
		redirectAttributes.addFlashAttribute("dto", dto);
		
		return "redirect:/sample/ex03";
	}
	
	@GetMapping("/ex03")
	public void ex03() {
		log.info("ex03");
	}

	@GetMapping({"/exLayout1", "/exLayout2", "/exTemplate", "/exSidebar"})
	public void exLayout1() {
		log.info("exLayout");
	}
	
	
}
