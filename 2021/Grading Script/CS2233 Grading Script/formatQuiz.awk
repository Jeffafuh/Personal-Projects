# FORMAT OF DATA:
# Use a tsv!
# Very first line of the sheet is the header line,
#   Notes \t LastName \t FirstName \t abc123 \t Q1 ... Qn \t Late \t Total \t Comments
# Immediately followed by all of the student records.
# You can store the rubric and other information below the records.
#
# SAMPLE RECORD:
#   Late Submission \t Doe \t John \t xyz987 \t 4 \t 5.8 \t 10 \t 3 \t -3 \t 19.8 \t Q2) Made a mistake. \t Q4) Need further explanation.    
# Modify the numStudents as necessary.

BEGIN{
    numStudents = 58;
    FS="\t";
    RS="\t*\r\n";
}

NR == 1{
    numQ = NF - 7;
    #Header on first line, extracts string literals for the Q's + late penalty
    for(i = 5; i < numQ+5+1; i++)
        qVals[i-5] = $i;
}

NR == 2, NR == numStudents+1{
    #print student information to know who's who
    printf("---------------------------\n");
    printf("%s, %s (%s)\n\n", $2, $3, $4);

    #print format header
    printf("Point Breakdown:\n");
    for(i = 0; i < numQ; i++)
    {
        printf("%s: %s\n", qVals[i], $(i+5));
    }
    printf("\n");

    isLate = 0;
    if( $(numQ+5) == "TRUE" )
    {
        isLate = 1;
        printf("Late Submission - No Credit\n");
    }
    #output total
    printf("Total: %s\n\n", $(numQ+6));

    #print out comments separated by a \n
    printf("Comments/Feedback:\n");
    for(i = numQ+7; i <= NF; i++)
    {
        printf("%s\n", $i);
    }
    #if there are no comments, print out a default message
    if(numQ+7 > NF && isLate == 0)
    {
        printf("Good work!\n");
    }
    printf("---------------------------\n");
}

