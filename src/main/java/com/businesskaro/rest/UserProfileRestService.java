package com.businesskaro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.model.BKException;
import com.businesskaro.model.BKUserProfileSummary;
import com.businesskaro.model.BkUserProfile;
import com.businesskaro.security.SecureTokenUtil;
import com.businesskaro.service.UserPersonalInfoService;

@RestController
public class UserProfileRestService extends BKRestService {
	
	@Autowired
	UserPersonalInfoService service;
	
	@Autowired
	SecureTokenUtil secureTokenUtil;

	@RequestMapping(value="/services/userProfile" , method = RequestMethod.POST)
	public BkUserProfile createUserProfile(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody BkUserProfile bkUserProfile){
		
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		
		bkUserProfile.details.userId = userId;
		bkUserProfile.summary.userId = userId;
		
		return service.createOrUpdateUserPersonalInfo(bkUserProfile, true);
	}
	
	@RequestMapping(value="/services/userProfile" , method = RequestMethod.PUT)
	public BkUserProfile updateUserProfile(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody BkUserProfile bkUserProfile){
		
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		bkUserProfile.details.userId = userId;
		bkUserProfile.summary.userId = userId;
		
		return service.createOrUpdateUserPersonalInfo(bkUserProfile, false);
	}
	
	@RequestMapping(value="/services/userProfile" , method = RequestMethod.GET)
	public BkUserProfile getUserProfile( 
			@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId ){
		
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		
		return service.getUserPersonalInfo(userId);
	}
	
	@RequestMapping(value="/services/userProfile/summary" , method = RequestMethod.GET)
	public BKUserProfileSummary getUserProfileSummary(@RequestParam("userId") String userId){
		//Integer userId = validateSecureToken(clientId, secureToken);
		return service.getUserPersonalSummary(Integer.parseInt(userId));
	}
	
	@RequestMapping(value="/services/userProfile/summarybyId/{userId}" , method = RequestMethod.GET)
	public BKUserProfileSummary getUserProfileSummaryById(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@PathVariable("userId") Integer userId){
		try{
			validateSecureToken(secureTokenUtil,clientId, secureToken);
			return service.getUserPersonalSummary(userId);
		} catch(Exception e){
			throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
	}
	
	@RequestMapping(value="/services/userProfile/details" , method = RequestMethod.GET)
	public BkUserProfile getUserProfileDetails( 
			@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId ){
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		return service.getUserPersonalInfo(userId);
	}
	@RequestMapping(value="/services/userProfile/details/{userId}" , method = RequestMethod.GET)
	public BkUserProfile getUserProfileDetailsById( 
			@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@PathVariable("userId") Integer userId){
		try{
			validateSecureToken(secureTokenUtil,clientId, secureToken);
			return service.getUserPersonalInfo(userId);
		} catch(Exception e){
			throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
	}
	
}