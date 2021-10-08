<?php
session_start();
ob_start();
if (isset($_SESSION['refreshHandler2']))unset($_SESSION['refreshHandler2']);
if(!isset($_SESSION['id'])) {

    header('location:login.php');
}

?>
    <!DOCTYPE html>
    <html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>McGill MyCoop</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class=" d-flex align-items-center justify-content-center" style="color: white">
                <div class="sidebar-brand-icon">
                    <img src="https://upload.wikimedia.org/wikipedia/en/thumb/2/29/McGill_University_CoA.svg/800px-McGill_University_CoA.svg.png" style="height: 50px">
                </div>
                <div class="sidebar-brand-text mx-2">McGill <sup>My-Coop</sup></div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
                <a class="nav-link" href="index.php">
                    <i class="fas fa-home"></i>
                    <span>Home</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                Panel
            </div>



            <!-- Nav Item - Student Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseStudent" aria-expanded="true" aria-controls="collapseStudent">
                    <i class="fas fa-user"></i>
                    <span>Students</span>
                </a>
                <div id="collapseStudent" class="collapse" aria-labelledby="headingStudent" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="studentsearch.php">Student Records</a>
                        <a class="collapse-item" href="studentmanagement.php">Student Management</a>
                    </div>
                </div>
            </li>

            <!-- Nav Item - Employer Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseEmployer" aria-expanded="true" aria-controls="collapseEmployer">
                    <i class="fas fa-industry"></i>
                    <span>Employers</span>
                </a>
                <div id="collapseEmployer" class="collapse" aria-labelledby="headingEmployer" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="employersearch.php">Employer Search</a>
                    </div>
                </div>
            </li>

            <!-- Nav Item - Messages Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseMessage" aria-expanded="true" aria-controls="collapseMessage">
                    <i class="fas fa-mail-bulk"></i>
                    <span>Messages</span>
                </a>
                <div id="collapseMessage" class="collapse" aria-labelledby="headingMessage" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="inbox.php">Inbox</a>
                        <a class="collapse-item" href="sendmessage.php">Send a Message</a>
                        <a class="collapse-item" href="sentmessages.php">Sent Messages</a>
                    </div>
                </div>
            </li>
            <!-- Nav Item - Messages Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseJobs" aria-expanded="true" aria-controls="collapseJobs">
                    <i class="fas fa-paperclip"></i>
                    <span>Jobs</span>
                </a>
                <div id="collapseJobs" class="collapse" aria-labelledby="headingJob" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="yourjobs.php">Your Jobs</a>
                        <a class="collapse-item" href="jobmanagement.php">Job Management</a>
                    </div>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTax" aria-expanded="true" aria-controls="collapseTax">
                    <i class="fas fa-pager"></i>
                    <span>Tax forms</span>
                </a>
                <div id="collapseTax" class="collapse" aria-labelledby="headingTax" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="taxForms.php">Download Tax Forms</a>
                    </div>
                </div>
            </li>

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>
        </ul>
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                    <!-- Sidebar Toggle (Topbar) -->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>

                    <!-- Topbar Search -->
                    <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">

                        <div class="input-group">
                            <input type="text" class="form-control bg-light border-0 small" placeholder="Search" aria-label="Search" aria-describedby="basic-addon2" style="height: 40px; " onkeyup="showResult(this.value)">

                            <div class="input-group-append">
                                <script>
                                    function showResult(str) {
                                        if (str.length==0) {
                                            document.getElementById("livesearch").innerHTML="";
                                            document.getElementById("livesearch").style.border="0px";
                                            return;
                                        }
                                        if (window.XMLHttpRequest) {
                                            // code for IE7+, Firefox, Chrome, Opera, Safari
                                            xmlhttp=new XMLHttpRequest();
                                        } else {  // code for IE6, IE5
                                            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                                        }
                                        xmlhttp.onreadystatechange=function() {
                                            if (this.readyState==4 && this.status==200) {
                                                document.getElementById("livesearch").innerHTML=this.responseText;
                                                document.getElementById("livesearch").style.border="1px solid #A5ACB2";
                                            }
                                        }
                                        xmlhttp.open("GET","livesearch.php?q="+str,true);
                                        xmlhttp.send();
                                    }
                                </script>
                                <button class="btn btn-primary" type="button">
                                    <i class="fas fa-search fa-sm"></i>
                                </button>
                            </div>
                        </div>
                        <div id="livesearch"></div>
                    </form>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">


                        <button class=" btn btn-primary btn-user btn-block" style="color: red" >
                            <h7 style="color: white"> Welcome <?php echo($_SESSION['username']) ?>  </h7>
                        </button>



                    </ul>

                </nav>
            <!-- End of Topbar -->

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-0 text-gray-800">Student Records</h1>
                </div>

                <!-- Content Row -->
                <div class="row">
                    <div class="col-md-12" >
                        <form type="hidden" method="get" >
                            <div class="card" id="tableshow"  <?php  if(isset($_SESSION['refreshHandler'])){ echo " style=\" display: none; \"  " ; } else echo " style=\" display: block; \"  " ;  ?> >
                                <div class="card-header">
                                    <strong class="card-title">Student Directory</strong>
                                </div>
                                <div class="card-body">
                                    <table id="bootstrap-data-table" class="table table-striped table-bordered">

                                        <?php
                                        $sira = 1;
                                        echo "<thead> <tr>
                            <th>Username</th>
                            <th>McgillID</th>
                            <th>Email</th>
                            <th>Status</th>";

                                        $showAllStud = 'https://ecse321-group12.herokuapp.com/students';


                                        $cSessionS = curl_init();
                                        curl_setopt($cSessionS,CURLOPT_URL,$showAllStud);
                                        curl_setopt($cSessionS,CURLOPT_RETURNTRANSFER,true);
                                        curl_setopt($cSessionS,CURLOPT_HEADER, false);
                                        $resultSs=curl_exec($cSessionS);
                                        curl_close($cSessionS);
                                        //   echo $resultSs;

                                        $converterSs = json_decode($resultSs);
                                        $resultstringSs = "";
                                        $resultstringEmail = "";
                                        $resultstringMcGillId = "";

                                        foreach ($converterSs as $key => $value) {

                                            $resultstringSs = $resultstringSs.($value->username).", ";
                                        }


                                        foreach ($converterSs as $key => $value) {

                                            $resultstringEmail = $resultstringEmail.($value->email).", ";
                                        }

                                        foreach ($converterSs as $key => $value) {

                                            $resultstringMcGillId = $resultstringMcGillId.($value->mcgillid).", ";
                                        }

                                        //       $_SESSION['studentsSs'] = " Student Usernames: ".$resultstringSs;

                                        $userNames_r = explode(', ', $resultstringSs);

                                        $userEmails_r = explode(', ', $resultstringEmail);

                                        $userMcGillId_r = explode (', ', $resultstringMcGillId);



                                        //  print_r($userNames_r);

                                        // header("Refresh:0");



                                     //   $idArray = explode(', ', $idmc);
                                        //     print_r($idArray);

                                        $acc = 10;
                                        $bb = -1;
                                        foreach ($userNames_r as $key) { $bb++; }

                                        for ($k =0; $k < $bb; $k++) {

                                            echo "<tr>";
                                            echo "<td>";
                                            echo $userNames_r[$k];
                                            echo "</td>";
                                            echo "<td>";


                                            echo $userMcGillId_r[$k];
                                            echo "</td>";



                                            echo "<td>";

                                            echo $userEmails_r[$k];
                                            echo "</td>";




                                            echo "<td>";
                                            $key;
                                            if(isset($_SESSION['studentconfirmation'])) {
                                                if (strpos($userNames_r[$k], $_SESSION['studenusername']) !== false) {
                                                    echo $_SESSION['studentconfirmation'];
                                                } else {
                                                    echo "Not Confirmed";
                                                }
                                            }else {
                                                echo "Not Confirmed";
                                            }


                                            echo "</td>";
                                            echo "<tr>";
                                            $sira++;
                                            $acc++;
                                        }

                                        ?>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-5">
                        <div class="  p-3">
                            <div class="border border">
                                <div class=" text-center" >
                                    <h1 class="h4 text-gray-900 mb-4">Search for a Student</h1>
                                </div>
                                <form class=" user" method="post" action="">
                                    <input type="text" class="form-control form-control-user" name="studentidsearch" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter a student name">
                                    <br class="br-1"><br>
                                    <h1 class="h5 text-gray-900 mb-4"> Result:</h1>
                                    <input type="text" class="form-control form-control-user" name="resultsearch2" value="<?php if(isset($_SESSION['studentsSingleSearch'])) { echo $_SESSION['studentsSingleSearch']; } ?>" id="exampleInputEmail">
                                    <br>
                                    <button class=" btn btn-primary btn-user btn-block"  type="submit" name="submitbutsearch2">
                                        Search Student
                                    </button>
                                    <br>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-5">
                        <div class="  p-3">
                            <style>
                                #myDIV {
                                    padding-top: 10px;
                                }
                                #btn11 {
                                    position: relative;
                                    margin-top 100px: ;
                                }
                            </style>
                            </head>


                            <button id="btn11" class="btn-group  btn-primary btn-user" style="border-radius: 10px;" onclick="myFunction()">Student Forms</button>

                            <div class="border border" id="myDIV" style="display: none" >


                                <h1 id="myDIV" class="h4 text-gray-900 mb-4">Student Forms</h1>

                                <form class=" user" method="post" action="">
                                    <div class="form-group">
                                        <input type="text" class="form-control form-control-user" name="studentname" id="exampleInputEmail" aria-describedby="emailHelp" value="<?php if(isset($_SESSION['studenusername'])) { echo $_SESSION['studenusername']; } ?>" placeholder="You should search for a student first." >
                                        <br>
                                    </div>

                                    <button class=" btn btn-primary btn-user btn-block order-sm-2 " style="width: 200px;" type="submit" name="downloadproof">
                                        Download Proof of Placement
                                    </button>
                                    <br>
                                </form>
                                <button onclick="myFunction2()" class=" btn btn-primary btn-user btn-block order-sm-2 " style="width: 200px;"  name="confirm">
                                    Confirm Student
                                </button>
                                <div>
                                    <br>
                                    <br>
                                    <form method="post" enctype="multipart/form-data" style="display: none;" id="confirm">
                                        <button class=" btn btn-primary btn-user btn-block order-sm-2 " style="width: 200px;" type="submit" name="uploadeval"> Upload Student Evaluation</button>
                                        <input type="file" name="myfile">
                                    </form>
                                </div>



                            </div>
                            <script>
                                function myFunction() {
                                    var x = document.getElementById("myDIV");
                                    if (x.style.display === "none") {
                                        x.style.display = "block";
                                    }

                                    var y = document.getElementById("tableshow");
                                    if (x.style.display == "block") {
                                        y.style.display = "none";
                                    }

                                }


                                function myFunction2() {
                                    var x = document.getElementById("confirm");
                                    if (x.style.display === "none") {
                                        x.style.display = "block";
                                    } else {
                                        x.style.display = "none";
                                    }
                                }


                            </script>
                        </div>

                    </div>

                    <div class="col-lg-5">

                    </div>



                </div>

                <!-- Content Row -->



                <!-- Content Row -->
                <div class="row">

                </div>
                    <!-- Content Column -->
                    <div class="col-lg-6 mb-4">


                        <!-- Color System -->
                        <div class="row">

                        </div>

                    </div>

                    <div class="col-lg-6 mb-4">


                    </div>
                </div>

            </div>
            <!-- /.container-fluid -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy; McGill MyCoop System</span>
                    </div>
                </div>
            </footer>
        </div>
        <!-- End of Main Content -->

        <!-- Footer -->

        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <a class="btn btn-primary" href="login.php">Logout</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="vendor/chart.js/Chart.min.js"></script>

<!-- Page level custom scripts -->
<script src="js/demo/chart-area-demo.js"></script>
<script src="js/demo/chart-pie-demo.js"></script>

</body>

</html>


<?php
if (isset($_POST['submitbutsearch'])) {

        $showStud = 'https://ecse321-group12.herokuapp.com/students';


        $cSession = curl_init();
        curl_setopt($cSession,CURLOPT_URL,$showStud);
        curl_setopt($cSession,CURLOPT_RETURNTRANSFER,true);
        curl_setopt($cSession,CURLOPT_HEADER, false);
        $result=curl_exec($cSession);
        curl_close($cSession);
         echo $result;

        $converter = json_decode($result);
        $resultstring = "";

        foreach ($converter as $key => $value) {

           $resultstring = $resultstring.($value->username).", ";
        }

    $_SESSION['students'] = " Student Usernames: ".$resultstring;
        header("Refresh:0");






} elseif (isset($_POST['submitbutsearch2'])){

    $searchStud = 'https://ecse321-group12.herokuapp.com/students/'.$_POST['studentidsearch'];


    $cSession = curl_init();
    curl_setopt($cSession,CURLOPT_URL,$searchStud);
    curl_setopt($cSession,CURLOPT_RETURNTRANSFER,true);
    curl_setopt($cSession,CURLOPT_HEADER, false);
    $result=curl_exec($cSession);
    curl_close($cSession);
    echo $result;

    $converter = json_decode($result);
    $studidcheck2 = $converter->username;
   // $doccheck = $converter->authoredDocumentsIds;

    if (strpos($studidcheck2, $_POST['studentidsearch']) !== false) {
        $resultstring = "";
       // foreach ($converter as $key => $value) {

       //     $resultstring = $resultstring."  ".($value->authoredDocumentsIds);
       // }

        $_SESSION['studentsSingleSearch'] = "Student ".$studidcheck2." exists in the system";//.$doccheck." files in the system.";
        $_SESSION['studenusername'] = $studidcheck2;
        header("Refresh:0");
        // print("Succesful");

    } else {
        $_SESSION['studentsSingleSearch'] = "There was an error please check your input";
        header("Refresh:0");
    }

    $_SESSION['refreshHandler'] = 1;

}


if (isset($_POST['downloadproof'])) {

    header('Location: https://drive.google.com/uc?export=download&id=1DUDKbQ4msT5cwwm9iQQ3y3TLaFBHWR-g');
}

if(isset($_POST['uploadeval'])){
   // header("Refresh:0");
    $_SESSION['studentconfirmation'] = 'Confirmed';
    unset($_SESSION['refreshHandler']);
    header("Refresh:0");

}


?>