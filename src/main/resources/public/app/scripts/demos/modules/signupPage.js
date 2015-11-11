angular
  .module('theme.demos.signup_page', [
    'theme.core.services'
  ])
  .controller('SignupPageController', ['$rootScope', '$scope', '$theme', '$timeout', 'UserAuthentication', '$location','$route', function($rootScope,$scope, $theme, $timeout, UserAuthentication, $location, $route) {
    'use strict';
    $theme.set('fullscreen', true);
    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });
    $scope.email ="";
    $scope.password ="";
    $scope.loginForm = {};
    $scope.form = {};
    $scope.loginForm.email = '';
    $scope.loginForm.password = '';
    $scope.serverMessage = '';
    $scope.waiting = false;

    var lastRoute = $route.current;
    $scope.$on('$locationChangeSuccess', function() {
      //console.log('$route is', $route);
    });
    $scope.logIn = function(){
      $scope.waiting = true;
      var apphistory = UserAuthentication.getuserRoutes();
      //console.log($scope.loginForm.password);
      //console.log($scope.loginForm.email);
      UserAuthentication.signInUser({email:$scope.loginForm.email, password:$scope.loginForm.password}).then(function(data){
        //$rootScope.profileCreated = data.profileCreated;
        UserAuthentication.getUserDetails().then(function(){
          $scope.waiting = false;
          $scope.$emit('loginsuccess', data);
          $scope.serverMessage = '';
          if(apphistory[0]==='/login'){
            //console.log('History is',history);
            $location.path('/'); 
          }
          else{
            $location.path(apphistory[0]);  
          }
        },function(){
            //Error
        });                 
      },function(error){
        $scope.showServerMessage = 'Your username or password was not correct';
        $scope.waiting = false;
      });
    };
    $scope.$on('$routeChangeStart', function() {
      $scope.previouspath = $location.path();
    });

    $scope.$on('$routeChangeSuccess', function() {
      //console.log('routeChangeSuccess success in signuppage.js', $location);
    });



    $scope.canSubmitLoginForm = function() {
      return $scope.form.loginForm.$valid;
    };

  }])
  .controller('NewSignUpController', ['$rootScope', '$scope', '$theme', '$timeout', 'UserAuthentication', '$location','$route', function($rootScope,$scope, $theme, $timeout, UserAuthentication, $location, $route) {
    $theme.set('fullscreen', true);
    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });
    $scope.email= '';
    $scope.password = '';
    $scope.repeatpassword = '';
    $scope.isrpwdError = false;
    $scope.serverMessage = '';
    $scope.waiting = false;

    $scope.$watch('repeatpassword',function(newval,oldval){
      if(newval){
        if(newval!==$scope.password){
          $scope.signupform.$setValidity('repeatpassword',false,$scope.signupform);
          $scope.isrpwdError = true;
          //$scope.signupform.repeatpassword.$error = true;
          //$scope.signupform.repeatpassword.$setValidity('repeatpassword',true,$scope.signupform);
        }else{
          $scope.signupform.$setValidity('repeatpassword',true,$scope.signupform);
          $scope.isrpwdError = false;
          //$scope.signupform.repeatpassword.$error = false;
          //$scope.signupform.repeatpassword.$setValidity('repeatpassword',false,$scope.signupform);
        }
      }

    });

    $scope.register = function($event){
      $event.preventDefault();
      
      $scope.showServerMessage = '';
      if($scope.email && $scope.password && $scope.repeatpassword){
        $scope.waiting = true;
        var apphistory = UserAuthentication.getuserRoutes(); 
        //console.log($scope.loginForm.password);
        //console.log($scope.loginForm.email);
        UserAuthentication.registerUser({userName:$scope.email, password:$scope.password, email:$scope.email}).then(function(data){
            UserAuthentication.signInUser({userName:$scope.email, password:$scope.password, email:$scope.email}).then(function(data){
                $scope.$emit('loginsuccess', data);
                $location.path('/userprofile/firstLogin'); 
              },function(error){

              });
              //$scope.$emit('loginsuccess', data);
              //$scope.serverMessage = '';

          //$scope.$emit('loginsuccess', data);
          //$scope.serverMessage = '';
          /*if(apphistory[0]==='/signupform'){
            //console.log('History is',history);
            $location.path('/'); 
          }
          else{
            $location.path(apphistory[0]);  
          }  */
          $scope.waiting = false;       
        },function(error){
          $scope.waiting = false;
          if(error.data.type ==='USER_ALREADY_EXIST'){
            $scope.showServerMessage = 'Email id has already been registered.';
          }else{
            $scope.showServerMessage = 'Unknow error. Please try again.';
          }        

        });
      }
    };

  }])
  .controller('RestPasswordController', ['$scope', '$theme', '$http', '$window',function($scope, $theme, $http, $window) {
    $scope.waiting = false;    
    $theme.set('fullscreen', true);
    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });
    $scope.reset = function(){
      $scope.waiting = true; 
    	console.log('In reset'+$scope.resetEmail);
    	$http({
            url: 'services/resetPassword',
            method: 'POST',
            data: {
          	  "email":$scope.resetEmail
            }
          }).then(function(response){
            $scope.waiting = false;    
        	  $window.location.href = '/#/login';
          },function(){
            $scope.waiting = false;
          });
    	
    }

  }]);