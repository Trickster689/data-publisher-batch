<h1>Data Publisher Batch</h1>

<h3>This application reads data from postgresql using spring Batch and
writes data to a .csv file</h3>

<p>
create a schema in your postgresql by the name 'testdb'.
The application will create a table name employee and insert some
dummy data into it using the  data.sql file in the resource folder.
</p>

<p>The application has two branches:</p>
<ul>
<li> jpa_reader_branch</li>
<li> jdbc_reader_branch</li>
</ul>

<p>
switch the branch to see the different implementation.

The output file will be in the output folder with  the name processed_data.csv
</p>


