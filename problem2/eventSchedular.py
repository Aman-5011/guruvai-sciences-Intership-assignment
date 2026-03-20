class EventScheduler:
    def can_attend_all(self, events):
        # if 0 or 1 events then no overlap
        if len(events) <= 1:
            return True
        
        events.sort() # Sort the events by their start time
        
        # start checking from 2nd idx for comparison with previous event
        for i in range(1, len(events)):
            previous_event=events[i-1]
            current_event=events[i]
            
            previous_end= previous_event[1]
            current_start= current_event[0]
            
            if current_start < previous_end:
                return False
                
        return True


    def min_rooms_required(self, events):
        if len(events) == 0:
            return 0
            
        # Create empty lists to hold start and end times
        starts = []
        ends = []
        
        # Loop through every event and separate the start and end times
        for event in events:
            start_time = event[0]
            end_time = event[1]
            
            starts.append(start_time)
            ends.append(end_time)
            
        # Sort both lists
        starts.sort()
        ends.sort()
        
        rooms_in_use=0
        max_rooms_needed=0
        
        # Setup our two pointers
        start_ptr=0
        end_ptr=0
        
        # Process all events until we run out of start times
        while start_ptr < len(events):
            
            # If a meeting starts before or at the exact time a previous one ends
            if starts[start_ptr] < ends[end_ptr]:
                rooms_in_use = rooms_in_use + 1
                
                # Update our max record if needed
                if rooms_in_use > max_rooms_needed:
                    max_rooms_needed = rooms_in_use
                    
                start_ptr = start_ptr + 1
                
            else:
                # A meeting has ended, freeing up a room
                rooms_in_use = rooms_in_use - 1
                end_ptr = end_ptr + 1
                
        return max_rooms_needed

if __name__ == "__main__":
    scheduler = EventScheduler()
    
    # Test Case 1
    events1 = [(9, 10), (10, 11), (11, 12)]
    print("Testing Schedule 1:", events1)
    print("Can attend all?", scheduler.can_attend_all(events1))
    print("Minimum rooms:", scheduler.min_rooms_required(events1))
    
    print("-" * 30)
    
    # Test Case 2
    events2 = [(9, 11), (10, 12), (9, 10)]
    print("Testing Schedule 2:", events2)
    print("Can attend all?", scheduler.can_attend_all(events2))
    print("Minimum rooms:", scheduler.min_rooms_required(events2))