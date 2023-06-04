# Unix timestamp converter test cases


## Test case #1: Different date formats and how they are converted to timestamps (API)

### Steps:
* Perform the following API call to convert date into timestamp use Date text from table as parameter
```
Endpoint	 - https://helloacm.com/api/unix-timestamp-converter/?cached&s={data}
Method	         - GET
```
* Validate response status is 200 OK
* Per table described, for parameter **date text**, result should be **1234828800**, this timestamp value should represent the date in GMT zone


|        Date text	        |
|:------------------------:|
|      "02/17/2009" 	      |
|     "17/02/2009"  	      | 
|     "2009/02/17"  	      | 
|      "2/17/2009"  	      | 
|      "17/2/2009"  	      | 
|      "2009/2/17"  	      | 
|     " 2/17/2009"  	      | 
|     "17/ 2/2009"  	      | 
|     "2009/ 2/17"  	      | 
|      "02172009"  	       | 
|      "17022009"  	       | 
|      "20090217"  	       | 
|      "Feb172009"  	      |
|      "17Feb2009"  	      |
|      "2009Feb17"  	      |
|  "17 February, 2009"  	  |
|      "17 Feb, 2009"      | 
|      "2009, Feb 17"      |
| "02/17/2009 00:00:00 AM" | 
|  "02/17/2009 00:00:00"	  |

## Test case #2: Timestamp and how they are converted to date (API)

### Steps:
* Perform the following API call to convert date into timestamp, use timestamp from table as parameter
```
Endpoint	 - https://helloacm.com/api/unix-timestamp-converter/?cached&s={timestamp}
Method	         - GET
```
* Validate response status is 200 OK
* Per table described, for parameter {date text}, result should be {result}

|   Timestamp	   |       Result        |
|:--------------:|:-------------------:|
|  1234828800 	  | 2009-02-17 00:00:00 |
| -1234828800  	 | 1930-11-15 00:00:00 |
|      0  	      | 1970-01-01 00:00:00 |
|   1456704000   | 2016-02-29 00:00:00 |

## Test case #3: Invalid values for conversion (API)

### Steps:
* Perform the following API call to convert date into timestamp use Date text from table as parameter
```
Endpoint	 - https://helloacm.com/api/unix-timestamp-converter/?cached&s={data}
Method	         - GET
```
* Validate response status is 400 BAD_REQUEST
* Validate body contains meaningful error message

|     Date text	      |
|:-------------------:|
|      asdasd 	       |
|  zxc2009-02-17dsf   |
| !@#2009-02-17pps  	 |
|        !#$(         |
 |        null         |
|      undefined      |

## Test case #4: Different date formats and how they are converted to timestamps (UI client)

### Steps:
* Go to https://helloacm.com/tools/unix-timestamp-converter/
* Enter value {Date text} into date string input field
* Click convert to button next to the field
* Verify conversion to timestamp is successful, result should be **1234828800**, this timestamp value should represent the date in GMT zone

|        Date text	        |
|:------------------------:|
|      "02/17/2009" 	      |
|     "17/02/2009"  	      | 
|     "2009/02/17"  	      | 
|      "2/17/2009"  	      | 
|      "17/2/2009"  	      | 
|      "2009/2/17"  	      | 
|     " 2/17/2009"  	      | 
|     "17/ 2/2009"  	      | 
|     "2009/ 2/17"  	      | 
|      "02172009"  	       | 
|      "17022009"  	       | 
|      "20090217"  	       | 
|      "Feb172009"  	      |
|      "17Feb2009"  	      |
|      "2009Feb17"  	      |
|  "17 February, 2009"  	  |
|      "17 Feb, 2009"      | 
|      "2009, Feb 17"      |
| "02/17/2009 00:00:00 AM" | 
|  "02/17/2009 00:00:00"	  |

## Test case #5: Timestamp and how they are converted to date (UI client)

### Steps:
* Go to https://helloacm.com/tools/unix-timestamp-converter/
* Enter value {timestamp} into timestamp input field
* Click convert to button next to the field
* Verify conversion to timestamp is successful, for timestamp {timestamp} result should be {result}, this date string value should represent the date in GMT zone

|   Timestamp	   |       Result        |
|:--------------:|:-------------------:|
|  1234828800 	  | 2009-02-17 00:00:00 |
| -1234828800  	 | 1930-11-15 00:00:00 |
|      0  	      | 1970-01-01 00:00:00 |
|   1456704000   | 2016-02-29 00:00:00 |

## Test case #6: Invalid values for conversion (UI client)

### Steps:
* Go to https://helloacm.com/tools/unix-timestamp-converter/
* Enter value {data} into timestamp input field
* Click convert to button next to the field
* Verify error is shown on invalid data conversion
* Repeat steps 1-4 for date string to timestamp conversion as well

|        Data	        |
|:-------------------:|
|      asdasd 	       |
|  zxc2009-02-17dsf   |
| !@#2009-02-17pps  	 |
|        !#$(         |
|        null         |
|      undefined      |