package com.apibrief3.controller;

import com.apibrief3.dto.CategoryDTO;
import com.apibrief3.dto.CommandDTO;
import com.apibrief3.exception.ValidationException;
import com.apibrief3.record.commandRequest.AddCommandRequest;
import com.apibrief3.service.CommandService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/commands")
public record CommandController(CommandService commandService) {
    @GetMapping
    public ResponseEntity<List<CommandDTO>> getAllCommands(){
        return ResponseEntity.ok().body(commandService.getAllCommands());
    }
    @GetMapping("/{commandId}")
    public ResponseEntity<CommandDTO> getCommand(@PathVariable Integer commandId){
        return ResponseEntity.ok().body(commandService.getCommand(commandId));
    }
    @PostMapping
    public ResponseEntity<Object> addCommand(@Valid @RequestBody AddCommandRequest addCommandRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new ValidationException(bindingResult.getFieldErrors());
        return ResponseEntity.status(HttpStatus.CREATED).body(commandService.addCommand(addCommandRequest));
    }
}
