import http from 'k6/http';
import {check} from 'k6';

const uri = 'http://127.0.0.1:9080/sarb/rate';

//Options for k6 to use while running tests
export const options = {
	thresholds: {
		http_req_failed: ['rate < 0.01'],
		http_req_duration: ['p(90) < 10'] //90% of all calls should be less than 10ms
	},
	scenarios: {
		default: {
			executor: 'constant-arrival-rate', //What the scenario is
			duration: '100s', // How long this scenario will last
			rate: 130, //Number of requests per seconds
			preAllocatedVUs: 100 //Virtual users: 100 threads
		}
	}
}

export default function() {
	response = http.get(uri+'/repo');
	
	check(response, {
		'succesful request': (r) => r.status === 200;
	})
}