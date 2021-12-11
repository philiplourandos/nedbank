import http from 'k6/http';
import {check, group} from 'k6';
import {SharedArray} from 'k6/data';

// Load key and expected value from file
const data = new SharedArray('static text data', function() {
    return JSON.parse(data.json).testresources;
});

const url = 'http://127.0.0.1:8080/nedbank/bpm/resourcebundle';
const params = {
    headers: {
        'Content-Type' : 'application/json'
    }
};

// Setup scenarios and thresholds
export const options = {
    thresholds: {
        http_req_failed: ['rate < 0.5'],
        http_req_duration: ['p(90) < 50', 'p(95) < 60', 'p(99) < 70'],
    },
    scenarios: {
        contacts: {
            executors: 'ramping-virtual-users',
            startVUs: 0,
            iterations: data.length,
            stages: [
                { duration: '30s',  target: 50},
                { duration: '100s', target: 150},
                { duration: '50s',  target: 300},
                { duration: '150s', target: 100}
            ],
            gracefulRampDown: '30s'
        }
    }
};

export default function () {
    const testData = data[scenario.iterationInTest];

    const response = http.get(url + '/' + testData.key);

    check(response, {
        'Request successful' : (r) => r.status() === 200,
        'Expected value returned': (r) => r.json('text') == testData.expected
    });
}
