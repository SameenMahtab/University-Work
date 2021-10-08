<?php
    session_start();
    ob_start();


if (isset($_SESSION['refreshHandler']))unset($_SESSION['refreshHandler']);


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

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-0 text-gray-800">Employer Search</h1>
                </div>

                <!-- Content Row -->
                <div class="row" <?php  if(isset($_SESSION['refreshHandler2'])){ echo " style=\" display: none; \"  " ; } else echo " style=\" display: block; \"  " ;  ?>>
                    <div class="col-md-12" >
                        <form type="hidden" method="get" >
                            <div class="card" >
                                <div class="card-header"  >
                                    <strong class="card-title">Student Directory</strong>
                                </div>
                                <div class="card-body">
                                    <table id="bootstrap-data-table" class="table table-striped table-bordered">

                                        <?php
                                        $sira = 1;
                                        echo "<thead> <tr>
                            <th>Username</th>
                            <th>Email</th>
                            <th>CoopJobs</th>";

                                        $showAllStud = 'https://ecse321-group12.herokuapp.com/employers';


                                        $cSessionS = curl_init();
                                        curl_setopt($cSessionS,CURLOPT_URL,$showAllStud);
                                        curl_setopt($cSessionS,CURLOPT_RETURNTRANSFER,true);
                                        curl_setopt($cSessionS,CURLOPT_HEADER, false);
                                        $resultSs=curl_exec($cSessionS);
                                        curl_close($cSessionS);
                                        //   echo $resultSs;

                                        $converterSs = json_decode($resultSs);
                                        $resultstringSs = "";
                                        $resultstringMail = "";

                                        foreach ($converterSs as $key => $value) {

                                            $resultstringSs = $resultstringSs.($value->username).", ";
                                        }
                                        foreach ($converterSs as $key => $value) {

                                            $resultstringMail = $resultstringMail.($value->email).", ";
                                        }

                                        $userNames_r = explode(', ', $resultstringSs);

                                        $userMail_r = explode(', ', $resultstringMail);

                                        /*     $resultstringNn = "";
                                             foreach ($converterSs as $key => $value) {

                                                 $resultstringNn = $resultstringNn.($value->coopJobsIds).", ";
                                             }

                                             //       $_SESSION['studentsSs'] = " Student Usernames: ".$resultstringSs;

                                             $coopJobIds = explode(', ', $resultstringNn);

                                             //  print_r($userNames_r);

                                             // header("Refresh:0");
                                         */


                                        foreach($userNames_r as $key) {
                                            $idmc = '';
                                            $characters = '0123456789';
                                            $randomId = '';
                                            $max = strlen($characters) - 1;
                                            for ($i = 0; $i < 3; $i++) {
                                                $randomId .= $characters[mt_rand(0, $max)];
                                            }
                                            $idmc = '260' . $randomId . ',';

                                            //     print $idmc;
                                        }
                                        $idArray = explode(', ', $idmc);
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


                                            echo $userMail_r[$k];

                                            echo "</td>";
                                            echo "<td>";

                                            $checkjobs = 'https://ecse321-group12.herokuapp.com/employers/'.$userNames_r[$k];


                                            $cSession = curl_init();
                                            curl_setopt($cSession,CURLOPT_URL,$checkjobs);
                                            curl_setopt($cSession,CURLOPT_RETURNTRANSFER,true);
                                            curl_setopt($cSession,CURLOPT_HEADER, false);
                                            $result=curl_exec($cSession);
                                            curl_close($cSession);
                                            // echo $result;

                                            $converter = json_decode($result);
                                            $conv_array = array($converter->coopJobsIds);

                                            //   print_r($conv_array);

                                            $resultstring = "";

                                            foreach($conv_array['0'] as $result1) {
                                                $resultstring = $resultstring.$result1.", ";
                                                // echo $result1, '<br>';
                                            }
                                            /*
                                                $resultstring = "";

                                                foreach ($converter as $key => $value) {

                                                    $resultstring = $resultstring."  ".($value->coopJobsIds);
                                                }

                                            */

                                            if (empty($converter->coopJobsIds)) { $_SESSION['searchJobs'] = "No Jobs";} else { $_SESSION['searchJobs']= $resultstring;}


                                            //  $_SESSION['yourjobs'] = " Your Jobs: ".$resultstring;
                                            echo $_SESSION['searchJobs'];





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
                <div class="row" style="position: center">
                    <div class="col-lg-7" >
                        <div class="  p-5"  id="no space">
                            <div class="border border">
                                <div class=" text-center" >
                                    <h1 class="h4 text-gray-900 mb-4">Search for an Employer</h1>
                                 </div>
                                 <form class=" user" method="post" action="">
                                     <div class="form-group">
                                         <input type="text" class="form-control form-control-user" name="empIdSearch" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter the employer name">
                                     </div>
                                     <h1 class="h5 text-gray-900 mb-4"> Result:</h1>

                                     <input type="text" class="form-control form-control-user" name="resultsearch" value="<?php if(isset($_SESSION['employerusername'])) { echo $_SESSION['employerusername']; } ?>" id="exampleInputEmail">
                                     <br>
                                    <button class=" btn btn-primary btn-user btn-block" type="submit" name="submitbutsearch">
                                     Search Employer
                                    </button>
                                 </form>
                            </div>
                        </div>
                    </div>

                </div>


                <!-- Content Row -->
                <div class="row">







                </div>

                <!-- Content Row -->

                <div class="row">

                </div>

                <!-- Content Row -->
                <div class="row">

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

        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; McGill MyCoop System</span>
                </div>
            </div>
        </footer>
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
        
        $showEmp = 'https://ecse321-group12.herokuapp.com/employers/'.$_POST['empIdSearch'];
        
        
        $cSession = curl_init();
        curl_setopt($cSession,CURLOPT_URL,$showEmp);
        curl_setopt($cSession,CURLOPT_RETURNTRANSFER,true);
        curl_setopt($cSession,CURLOPT_HEADER, false);
        $result=curl_exec($cSession);
        curl_close($cSession);
       // echo $result;
        
        $converter = json_decode($result);
        $resulstring = $converter->username;

        if (isset($resulstring) != false) {
            $printer = "Employer " . $resulstring . " exists in the system";
            $_SESSION['employerusername'] = $printer;
            $_SESSION['refreshHandler2'] = 1;

            header("Refresh:0");
        } else {
            $printer = "Employer " . $_POST['empIdSearch'] . " does not exists in the system";
            $_SESSION['employerusername'] = $printer;
            $_SESSION['refreshHandler2'] = 1;

            header("Refresh:0");
        }


        
        
            
        }
        else {
            $_SESSION['studentsSingleSearch'] = "There was an error please check your input";
           // header("Refresh:0");
             }
    
    
    
    
    
    ?>

