package app;


class PassableServices{
    ApplicationRunner applicationRunner;
    //allows user to pass services initalized by the application runner
    PassableServices(ApplicationRunner applicationRunner){
        this.applicationRunner = applicationRunner;
    }
}