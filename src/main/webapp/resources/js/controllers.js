registrationApp.controller('registrationController',['$scope','registrationService',function($scope, registrationService){
	$scope.user={firstName:'', lastName:'', age:'', id:''};
	$scope.users=null;
	$scope.error = null;
	
	$scope.resetForm = function(){
		$scope.user = {firstName:'', lastName:'', age:'', id:''};
		$scope.userForm.$setPristine();
		$scope.userForm.$setUntouched();		
	}
	$scope.createUser = function(){		
		if($scope.user.id != null && $scope.user.id != ""){
			$scope.updateUser();	
		}else{
			registrationService.createUser($scope.user).then(function(response){
				console.log(response);
				if(response.data !== undefined){
					$scope.error = response.data.message;
				}else{
					$scope.getUsers();
					$scope.resetForm();
				}
				
			});
		}
	}
	
	$scope.getUsers = function(){
		registrationService.getUsers().then(function(response){
			if(response.data !== undefined){
				$scope.error = response.data.message;
			}else{
				$scope.users = response;
			}
		});
	}
	
	$scope.getUsers();

	$scope.updateUser = function(){		
		registrationService.updateUser($scope.user).then(function(response){
			if(response.data !== undefined){
				$scope.error = response.data.message;
			}else{
				$scope.user = response;
				$scope.getUsers();
				$scope.resetForm();
			}
		});
	}
	
	$scope.deleteUser = function(user){		
		registrationService.deleteUser(user).then(function(response){
			if(response.data !== undefined){
				$scope.error = response.data.message;
			}else{
				$scope.getUsers();
			}

		});
	}
	
	$scope.setupFormForUpdate = function(user){
		angular.copy(user, $scope.user);
	}

}]);