with Ada.Text_IO; use Ada.Text_IO;
with GNAT.Semaphores; use GNAT.Semaphores;

procedure Main is
   task type Philosopher is
      entry Start(Id : Integer);
   end Philosopher;

   Forks : Array (1..5) of Counting_Semaphore(1, Default_Ceiling);

   task body Philosopher is
      Id : Integer;
      Left_Fork_Id, Right_Fork_Id : Integer;
   begin
      accept Start (Id : in Integer) do
         Philosopher.Id := Id;
      end Start;
      Left_Fork_Id := Id;
      Right_Fork_Id := Id rem 5 + 1;

      for I in 1..10 loop
         Put_Line("Philosopher " & Id'Img & " is thinking for " & I'Img & " time");

         if Left_Fork_Id < Right_Fork_Id then
            Forks(Left_Fork_Id).Seize;
            Put_Line("Philosopher " & Id'Img & " took the left fork");

            Forks(Right_Fork_Id).Seize;
            Put_Line("Philosopher " & Id'Img & " took the right fork");
         else
            Forks(Right_Fork_Id).Seize;
            Put_Line("Philosopher " & Id'Img & " took the right fork");

            Forks(Left_Fork_Id).Seize;
            Put_Line("Philosopher " & Id'Img & " took the left fork");
         end if;

         Put_Line("Philosopher " & Id'Img & "is eating for " & I'Img & " time");

         if Left_Fork_Id < Right_Fork_Id then
            Forks(Right_Fork_Id).Release;
            Put_Line("Philosopher " & Id'Img & " put down the right fork");

            Forks(Left_Fork_Id).Release;
            Put_Line("Philosopher " & Id'Img & " put down the left fork");
         else
            Forks(Left_Fork_Id).Release;
            Put_Line("Philosopher " & Id'Img & " put down the left fork");

            Forks(Right_Fork_Id).Release;
            Put_Line("Philosopher " & Id'Img & " put down the right fork");
         end if;
      end loop;
   end Philosopher;

   Philosophers : array (1..5) of Philosopher;
Begin
   for I in Philosophers'Range loop
      Philosophers(I).Start(I);
   end loop;
end Main;
