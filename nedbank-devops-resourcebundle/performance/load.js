import http from 'k6/http';
import {check, sleep} from 'k6';
import {SharedArray} from 'k6/data';

// Load key and expected value from file
const data = new SharedArray('static text data', function() {
    return JSON.parse(open('data.json')).textresources;
});

const url = 'http://127.0.0.1:9080/nedbank/bpm/resourcebundle';
const params = {
    headers: {
        'Content-Type' : 'application/json'
    }
};

// Setup scenarios and thresholds
export const options = {
    thresholds: {
        http_req_failed: ['rate < 0.5'],
        http_req_duration: ['p(90) < 2', 'p(95) < 5', 'p(99) < 8'],
    },
    scenarios: {
        constRateTest: {
            executor: 'constant-arrival-rate',
            duration: '200s',
            rate: 130,
            preAllocatedVUs: 150
        },
        rampTest: {
            executor: 'ramping-vus',
            startVUs: 100,
            startTime: '15s',
            
            stages: [
                { duration: '30s',  target: 10 },
                { duration: '100s', target: 30 },
                { duration: '150s', target: 90 },
                { duration: '115s', target: 80 },
                { duration: '120s', target: 60 },
                { duration: '300s', target: 55 },
                { duration: '400s', target: 70 },
                { duration: '300s', target: 100 },
                { duration: '100s', target: 120 },
            ],
            gracefulRampDown: '30s'
        }
    }
};

export default function () {
    // Use all keys to submit to resource bundle service.
    for(let index = 0; index < data.length; index++) {
        const response = http.get(url + '/' + data[index].key);

        check(response, {
            'Request successful' : (r) => r.status === 200,
            'Expected value returned': (r) => r.json('text') === data[index].expected
        });
    }

    sleep(0.2);
}
