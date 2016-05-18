registrationApp.service('registrationService', function($resource){
	var User = $resource('/UserRegistration/users',{},
		{ 'get':    {method:'GET'},
		  'save':   {method:'POST'},
		  'update': {method:'PUT'},
		  'query':  {method:'GET', isArray:true},
		  'remove': {method:'DELETE', params: {id: 'id'}}
		}
	);

	this.createUser = function(userPassed){
		return User.save(userPassed).$promise.then(function(user) {
	    	return user;
	    },function(error) {
	    	return error;
       });
	}
	
	this.updateUser = function(userPassed){
		return User.update(userPassed).$promise.then(function(user) {
	    	return user;
	    },function(error) {
	    	return error;
      });
	}
	
	this.getUsers = function(){
		return User.query().$promise.then(function(users) {
	    	return users;
	    },function(error) {
           return error;
	    });
	}

	this.deleteUser = function(userPassed){
		return User.remove({id:userPassed.id}).$promise.then(function(data) {
	    	return data;
	    },function(error) {
            return error;
	    });
	}

});