package com.demo.controller;

import com.demo.model.AccountType;
import com.demo.model.VmDetail;
import com.demo.model.VmSortByMemory;
import com.demo.repository.VmDetailRepository;
import com.demo.repository.VmUserRepository;
import com.demo.model.VmUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class VmUserController {
    public static final Logger logger = LoggerFactory.getLogger(VmUserController.class);

   // @Autowired
    private VmUserRepository vmUserRepository;
    private VmDetailRepository vmDetailRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public VmUserController(VmUserRepository vmUserRepository, VmDetailRepository vmDetailRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.vmUserRepository = vmUserRepository;
        this.vmDetailRepository = vmDetailRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/User/signUp")
    public ResponseEntity<String> userSignUp(@RequestBody VmUser newVmUser){
        try {
            logger.info("/User/signUp called for new Vm user");
            newVmUser.setPassword(bCryptPasswordEncoder.encode(newVmUser.getPassword()));
            VmUser persistedVmUser = vmUserRepository.save(newVmUser);
            return new ResponseEntity<String>("New vmUser successfuly created", HttpStatus.CREATED);
        }catch(Exception ex){
            logger.error("Exception in /User/signUp: ",ex.getMessage());
            return new ResponseEntity<String>("Not able to create new vm user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Vm/vmProvision")
    public ResponseEntity<String> vmProvisioning(@RequestBody VmDetail newVmDetail) {
        try {
            logger.info("/Vm/vmProvision called for new Vm");
            vmDetailRepository.save(newVmDetail);
            return new ResponseEntity<String>("New vmDetail successfuly created", HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error("Exception in /Vm/vmProvision: "+ex.getMessage(),ex);
            return new ResponseEntity<String>("Not able to create new vm Detail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/displayVm/user/{userId}")
    public ResponseEntity<List> listVmByUser(@PathVariable(value = "userId") Long userId) {
        try {
            logger.info("/displayVm/user/{userId} called for displaying VM");
            List<VmDetail> vmList = null;
            Optional<VmUser> vmUserContainer = vmUserRepository.findById(userId);
            VmUser vmUser = vmUserContainer.get();
            if(vmUser.getAccountType()== AccountType.NON_MASTER){
                vmList = vmUser.getVmList();
            }else{
                vmList = vmDetailRepository.findAll();
            }
            return new ResponseEntity<List>(vmList, HttpStatus.FOUND);
        } catch (Exception ex) {
            logger.error("/displayVm/user/{userId}: ", ex.getMessage());
            return new ResponseEntity<List>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @PostMapping("/top_n_VM_ByMemory/user/{n}")
    public ResponseEntity<List> listTopN_VM_ByMemory(@PathVariable(value = "n") Long n) {
        try {
            logger.info("/top_n_VM_ByMemory/user/{n} called for displaying VM");
            List<VmDetail> vmList = null;
            List<VmDetail> topNList = null;
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            VmUser logInUser = vmUserRepository.findByUsername(user);
            if(logInUser.getAccountType()== AccountType.NON_MASTER){
                vmList = logInUser.getVmList();
            }else{
                vmList = vmDetailRepository.findAll();
            }
            if(vmList!=null){
                Collections.sort(vmList,new VmSortByMemory());
                topNList = new ArrayList<VmDetail>();
                int i=0;
                while(i<n && i<vmList.size()){
                    topNList.add(vmList.get(i));
                    i++;
                }
            }
            return new ResponseEntity<List>(topNList, HttpStatus.FOUND);
        } catch (Exception ex) {
            logger.error("Exception in /top_n_VM_ByMemory/user/{n}: ", ex.getMessage());
            return new ResponseEntity<List>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteUser/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "userId") Long userId) {
        try {
            String msg="";
            logger.info("/deleteUser/user/{userId} called for deleting user");
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            VmUser logInUser = vmUserRepository.findByUsername(user);
            if(logInUser.getAccountType()== AccountType.MASTER){
                vmUserRepository.deleteById(userId);
                msg="User account deleted for userId: "+userId;
            }else{
                msg="Non Master account cannot delete other accounts";
            }
            return new ResponseEntity<String>(msg, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Exception in /deleteUser/user/{userId}: ", ex.getMessage());
            return new ResponseEntity<String>("Not able to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/top_n_VM_ByMemory/user/{userId}/{n}")
    public ResponseEntity<List> listTopN_VM_ByMemoryForUserId(@PathVariable(value = "userId") Long userId,@PathVariable(value = "n") Long n) {
        try {
            logger.info("/top_n_VM_ByMemory/user/{userId}/{n} called for displaying top VM by user id");
            List<VmDetail> vmList = null;
            List<VmDetail> topNList = null;
            Optional<VmUser> vmUserContainer = vmUserRepository.findById(userId);
            VmUser vmUser = vmUserContainer.get();
            if(vmUser.getAccountType()== AccountType.NON_MASTER){
                vmList = vmUser.getVmList();
            }else{
                vmList = vmDetailRepository.findAll();
            }
            if(vmList!=null){
                Collections.sort(vmList,new VmSortByMemory());
                topNList = new ArrayList<VmDetail>();
                int i=0;
                while(i<n && i<vmList.size()){
                    topNList.add(vmList.get(i));
                    i++;
                }
            }
            return new ResponseEntity<List>(topNList, HttpStatus.FOUND);
        } catch (Exception ex) {
            logger.error("/top_n_VM_ByMemory/user/{userId}/{n}: ", ex.getMessage());
            return new ResponseEntity<List>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("User/getAllUser")
    public ResponseEntity<List> getAllUser(){
        try {
            logger.info("/getAllUser called for Vm user");
            List vmUserList = vmUserRepository.findAll();
            return new ResponseEntity<List>(vmUserList, HttpStatus.FOUND);
        }catch(Exception ex){
            logger.error("Exception in /User/getAllUser: ",ex.getMessage());
            return new ResponseEntity<List>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
